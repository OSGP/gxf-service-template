// SPDX-FileCopyrightText: Contributors to the GXF project
//
// SPDX-License-Identifier: Apache-2.0

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.22")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.7.22")

    implementation("org.springframework.boot:spring-boot-gradle-plugin:3.0.4")
    implementation("io.spring.gradle:dependency-management-plugin:1.1.0")

    implementation("com.github.davidmc24.gradle.plugin:gradle-avro-plugin:1.6.0")
}
