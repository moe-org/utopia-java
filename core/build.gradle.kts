//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
// The build.gradle is a part of project utopia, under MIT License.
// See https://opensource.org/licenses/MIT for license information.
// Copyright (c) 2021 moe-org All rights reserved.
//* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *


import moe.kawayi.org.utopia.gradle.ProjectDefinition
import moe.kawayi.org.utopia.gradle.tasks.GenerateVersion

// 插件设置
plugins {
    // java 库
    id("java-library")

}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(ProjectDefinition.JAVA_VERSION))
}


// 依赖
dependencies {
    // netty
    api("io.netty:netty-all:${ProjectDefinition.NETTY_VERSION}")

    // jackson
    api("com.fasterxml.jackson.core:jackson-databind:${ProjectDefinition.JACKSON_VERSION}")

    // 引入HOCON
    api("com.typesafe:config:${ProjectDefinition.HOCON_VERSION}")

    // 日志库
    // 我们已经提供了对外日志接口，所以不开放相关库
    implementation("org.apache.logging.log4j:log4j-api:${ProjectDefinition.LOG4J2_VERSION}")
    implementation("org.apache.logging.log4j:log4j-core:${ProjectDefinition.LOG4J2_VERSION}")

    // 依赖注入
    api("com.google.inject:guice:${ProjectDefinition.GUICE_VERSION}")

    // 日志彩色输出支持
    implementation("org.fusesource.jansi:jansi:${ProjectDefinition.JANSI_VERSION}")

    // 噪音库
    api("org.spongepowered:noise:${ProjectDefinition.NOISE_VERSION}")
}

tasks.register<GenerateVersion>("generateVersion"){
    this.generate(project)
}
