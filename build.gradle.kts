//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The ignore.groovy is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessExtensionImpl
import com.diffplug.gradle.spotless.SpotlessExtensionPredeclare
import com.diffplug.gradle.spotless.SpotlessPlugin
import moe.kawayi.org.utopia.gradle.ProjectDefinition
import moe.kawayi.org.utopia.gradle.tasks.AllJavaSourceJar
import moe.kawayi.org.utopia.gradle.tasks.AllJavadocJar
import moe.kawayi.org.utopia.gradle.tasks.AllJavadocTask
import org.apache.tools.ant.taskdefs.Java
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTask
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTaskMirror
import org.gradle.internal.impldep.org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutorService.TestTask
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.jvm.internal.Ref.IntRef
import org.gradle.internal.os.OperatingSystem

// 引入gradle插件
buildscript {
    repositories {
        maven{
            url = uri("https://maven.aliyun.com/repository/public/")
        }
        maven{
            url = uri("https://maven.aliyun.com/repository/spring/")
        }
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
        mavenCentral()
        gradlePluginPortal()
        google()
    }
    dependencies {
        classpath("com.diffplug.spotless:spotless-plugin-gradle:6.7.2")
    }
}

plugins {
    id("java")

    // 检查依赖许可证
    id("com.github.jk1.dependency-license-report") version "2.5"

    id("checkstyle")
    id("jacoco")
    id("com.diffplug.spotless") version "6.19.0"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(ProjectDefinition.JAVA_VERSION))
    }
}

//==========================================
ProjectDefinition.initializeForProject(rootProject);

//==========================================
allprojects {
    // 基本信息设置
    group = ProjectDefinition.GROUP_NAME

    version = ProjectDefinition.getVersion()

    repositories{
        mavenCentral()
        google()
        gradlePluginPortal()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

//==========================================
subprojects{
    // 设置插件
    apply<CheckstylePlugin>()
    apply<JacocoPlugin>()
    apply<SpotlessPlugin>()

    dependencies{
        checkstyle("com.puppycrawl.tools:checkstyle:${ProjectDefinition.CHECKSTYLE_VERSION}")
        checkstyle(files("${rootDir}/config/checkstyle/keep-great-check-1.0-SNAPSHOT.jar"))
    }

    //==========================================
    afterEvaluate {
        dependencies{
            testImplementation(platform("org.junit:junit-bom:${ProjectDefinition.JUNIT_VERSION}"))
            testImplementation("org.junit.jupiter:junit-jupiter")
        }

        configure<CheckstyleExtension> {
            isIgnoreFailures = false
            configProperties.put("utopia.checkstyle.small_hump", "[a-z]+[A-Za-z0-9]*")
            configProperties.put("utopia.checkstyle.large_hump", "[A-Z]+[A-Za-z0-9]*")
            configProperties.put("utopia.checkstyle.always_large", "[A-Z]+[A-Z0-9_]*")
        }

        configure<JacocoPluginExtension> {
            toolVersion = ProjectDefinition.JACOCO_VERSION
        }

        configure<SpotlessExtension> {
            java {
                palantirJavaFormat("2.33.0")
                toggleOffOn()
                importOrder("java", "javax", "moe.kawayi.org", "", "\\#")
                removeUnusedImports()
            }
        }
        configure<JavaPluginExtension> {
            toolchain.languageVersion.set(JavaLanguageVersion.of(20))
        }

        //==========================================
        tasks.withType<Checkstyle>().configureEach {
            this.dependsOn(tasks.named("test"))
            this.reports.xml.required = true
            this.reports.html.required = true
            this.checkstyleClasspath.plus(files("${rootDir}/config/checkstyle/keep-great-check-1.0-SNAPSHOT.jar"))
        }

        tasks.named("test") {
            this.finalizedBy(tasks.named("jacocoTestReport"))
        }

        // 设置测试
        tasks.named<Test>("test") {
            this.useJUnitPlatform()
        }

        tasks.named<JavaCompile>("compileJava") {
            options.encoding = "UTF-8"
            options.forkOptions.jvmArgs!!.add("-Dfile.encoding=UTF-8")
            options.forkOptions.jvmArgs!!.add("-Duser.language=es")
            // options.compilerArgs.add("-Werror")
            options.compilerArgs.add("-Xlint:all")
            // options.compilerArgs.add("-Xdoclint")
            options.isVerbose = true
        }
    }
}

//===============================
tasks.register("printVersion") {
    println(rootProject.version)
}

// 声明
tasks.register<AllJavadocTask>("allJavadoc")
tasks.register<AllJavaSourceJar>("allJavaSourceJar")
tasks.register<AllJavadocJar>("allJavadocJar")
//===============================
gradle.projectsEvaluated{
    tasks.named<AllJavadocTask>("allJavadoc"){
        this.initializeForRootProject(rootProject)
    }
    tasks.named<AllJavaSourceJar>("allJavaSourceJar"){
        this.initializeForRootProject(rootProject)
    }
    tasks.named<AllJavadocJar>("allJavadocJar"){
        this.initializeForRootProject(rootProject)
    }
}
