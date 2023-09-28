// SPDX-FileCopyrightText: Contributors to the GXF project
//
// SPDX-License-Identifier: Apache-2.0

import io.spring.gradle.dependencymanagement.internal.dsl.StandardDependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.4" apply false
    id("io.spring.dependency-management") version "1.1.3" apply false
    kotlin("jvm") version "1.9.10" apply false
    kotlin("plugin.spring") version "1.9.10" apply false
    kotlin("plugin.jpa") version "1.9.10" apply false
    id("com.github.davidmc24.gradle.plugin.avro") version "1.8.0" apply false
    id("org.sonarqube") version "4.2.1.3168"
    id("eclipse")
}

version = System.getenv("GITHUB_REF_NAME")?.replace("/", "-")?.lowercase() ?: "develop"

sonarqube {
    properties {
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.projectKey", "OSGP_gxf-service-template")
        property("sonar.organization", "gxf")
    }
}
tasks.sonar

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "eclipse")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

    group = "org.gxf.template"
    version = rootProject.version

    repositories {
        mavenCentral()
    }

    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    extensions.configure<StandardDependencyManagementExtension> {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
