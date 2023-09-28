//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The build.gradle is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

import moe.kawayi.org.utopia.gradle.ProjectDefinition
import moe.kawayi.org.utopia.gradle.config.ConfigJar
import moe.kawayi.org.utopia.gradle.tasks.ReleaseTask

// 插件设置
plugins {
    // java 程序
    id("java-library")
}

// LWJGL
val lwjglVersion = ProjectDefinition.LWJGL_VERSION

val lwjglNatives = Pair(
        System.getProperty("os.name")!!,
        System.getProperty("os.arch")!!
).let { (name, arch) ->
    when {
        arrayOf("Linux", "FreeBSD", "SunOS", "Unit").any { name.startsWith(it) } ->
            if (arrayOf("arm", "aarch64").any { arch.startsWith(it) })
                "natives-linux${if (arch.contains("64") || arch.startsWith("armv8")) "-arm64" else "-arm32"}"
            else
                "natives-linux"

        arrayOf("Mac OS X", "Darwin").any { name.startsWith(it) } ->
            "natives-macos"

        arrayOf("Windows").any { name.startsWith(it) } ->
            "natives-windows"

        else -> throw Error("Unrecognized or unsupported platform. Please set \"lwjglNatives\" manually")
    }
}

// 依赖
dependencies {
    // 依赖core
    api(project(":core"))
    api(project(":server"))

    // LWJGL
    api(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))
    api("org.lwjgl", "lwjgl")
    api("org.lwjgl", "lwjgl-assimp")
    api("org.lwjgl", "lwjgl-bgfx")
    api("org.lwjgl", "lwjgl-cuda")
    api("org.lwjgl", "lwjgl-egl")
    api("org.lwjgl", "lwjgl-fmod")
    api("org.lwjgl", "lwjgl-freetype")
    api("org.lwjgl", "lwjgl-glfw")
    api("org.lwjgl", "lwjgl-harfbuzz")
    api("org.lwjgl", "lwjgl-hwloc")
    api("org.lwjgl", "lwjgl-jawt")
    api("org.lwjgl", "lwjgl-jemalloc")
    api("org.lwjgl", "lwjgl-ktx")
    api("org.lwjgl", "lwjgl-libdivide")
    api("org.lwjgl", "lwjgl-llvm")
    api("org.lwjgl", "lwjgl-lmdb")
    api("org.lwjgl", "lwjgl-lz4")
    api("org.lwjgl", "lwjgl-meow")
    api("org.lwjgl", "lwjgl-meshoptimizer")
    api("org.lwjgl", "lwjgl-nanovg")
    api("org.lwjgl", "lwjgl-nfd")
    api("org.lwjgl", "lwjgl-nuklear")
    api("org.lwjgl", "lwjgl-odbc")
    api("org.lwjgl", "lwjgl-openal")
    api("org.lwjgl", "lwjgl-opencl")
    api("org.lwjgl", "lwjgl-opengl")
    api("org.lwjgl", "lwjgl-opengles")
    api("org.lwjgl", "lwjgl-openvr")
    api("org.lwjgl", "lwjgl-openxr")
    api("org.lwjgl", "lwjgl-opus")
    api("org.lwjgl", "lwjgl-ovr")
    api("org.lwjgl", "lwjgl-par")
    api("org.lwjgl", "lwjgl-remotery")
    api("org.lwjgl", "lwjgl-rpmalloc")
    api("org.lwjgl", "lwjgl-shaderc")
    api("org.lwjgl", "lwjgl-spvc")
    api("org.lwjgl", "lwjgl-sse")
    api("org.lwjgl", "lwjgl-stb")
    api("org.lwjgl", "lwjgl-tinyexr")
    api("org.lwjgl", "lwjgl-tinyfd")
    api("org.lwjgl", "lwjgl-tootle")
    api("org.lwjgl", "lwjgl-vma")
    api("org.lwjgl", "lwjgl-vulkan")
    api("org.lwjgl", "lwjgl-xxhash")
    api("org.lwjgl", "lwjgl-yoga")
    api("org.lwjgl", "lwjgl-zstd")
    runtimeOnly("org.lwjgl", "lwjgl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-assimp", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-bgfx", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-freetype", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-harfbuzz", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-hwloc", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-jemalloc", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-ktx", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-libdivide", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-llvm", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-lmdb", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-lz4", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-meow", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-meshoptimizer", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-nanovg", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-nfd", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-nuklear", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-openal", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opengles", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-openvr", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-openxr", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opus", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-ovr", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-par", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-remotery", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-rpmalloc", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-shaderc", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-spvc", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-sse", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-stb", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-tinyexr", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-tinyfd", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-tootle", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-vma", classifier = lwjglNatives)
    if (lwjglNatives == "natives-macos") runtimeOnly("org.lwjgl", "lwjgl-vulkan", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-xxhash", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-yoga", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-zstd", classifier = lwjglNatives)

    // https://mvnrepository.com/artifact/com.badlogicgames.gdx/gdx
    api("com.badlogicgames.gdx:gdx:${ProjectDefinition.LIBGDX_VERSION}")
    api("com.badlogicgames.gdx:gdx-backend-lwjgl3:${ProjectDefinition.LIBGDX_VERSION}")
    api("com.badlogicgames.gdx:gdx-platform:${ProjectDefinition.LIBGDX_VERSION}:natives-desktop")

    // TuningFork
    api("com.github.Hangman:TuningFork:${ProjectDefinition.TUNINGFORK_VERSION}")
}

// 打包
tasks.register<Jar>("releaseJar") {
    archiveClassifier.set("release")

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })

    destinationDirectory.set(ProjectDefinition.getDesktopReleaseDir())

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

ConfigJar.configJarForDesktop(tasks.named<Jar>("releaseJar").get())

// 发布
tasks.register<ReleaseTask>("releaseFile") {
    this.initializeForProject(project,ProjectDefinition.getDesktopReleaseDir())

    //=====---------- copy source ----------=====
    // copy icon
    this.releaseFile(
            file("${rootDir}/icon/Utopia(32x32).png"),
            ProjectDefinition.getIconDir());
    this.releaseFile(
            file("${rootDir}/icon/Utopia(128x128).png"),
            ProjectDefinition.getIconDir());
    // copy font
    this.releaseFile(
            file("${rootDir}/assets/fonts/unifont.ttf"),
            ProjectDefinition.getAssetsDir());
}

tasks.register("release"){
    this.dependsOn(tasks.named("releaseJar"))
    this.dependsOn(rootProject.tasks.named("allJavaSourceJar"))
    this.dependsOn(rootProject.tasks.named("allJavadocJar"))
    this.dependsOn(rootProject.tasks.named("releaseFile"))
}

tasks.register<JavaExec>("run"){
    this.dependsOn(tasks.named("releaseFile"))
    this.dependsOn(tasks.named("compileJava"))

     javaLauncher.set(javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(21))
    })

    this.workingDir = ProjectDefinition.getDesktopReleaseDir()

    maxHeapSize = "2G"
    systemProperties["file.encoding"] = "UTF-8"

    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set(ProjectDefinition.DESKTOP_START_CLASS)
}
