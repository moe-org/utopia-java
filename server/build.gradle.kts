//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The build.gradle is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *


import moe.kawayi.org.utopia.gradle.ProjectDefinition
import moe.kawayi.org.utopia.gradle.config.ConfigJar

import java.nio.charset.Charset
import java.nio.file.Files
import java.util.stream.Stream

plugins {
    // java 库
    id("java-library")

    // 打包jar
    id("com.github.johnrengelman.shadow") version "8.1.1"
}


dependencies {
    // core
    api(project(":core"))
}

// 打包
tasks.register<Jar>("releaseJar") {
    archiveClassifier.set("release")

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

ConfigJar.configJarForServer(tasks.named<Jar>("releaseJar").get())

// 发布
tasks.register("release") {
    this.description = "package and copy assets"
    dependsOn(tasks.named("releaseJar"))
    dependsOn(rootProject.tasks.named("allJavaSourceJar"))
    dependsOn(rootProject.tasks.named("allJavadocJar"))
}

