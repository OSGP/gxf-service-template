// SPDX-FileCopyrightText: Contributors to the GXF project
//
// SPDX-License-Identifier: Apache-2.0

plugins {
    id("gxf.spring-conventions")
    id("com.github.davidmc24.gradle.plugin.avro")
}

dependencies {
    implementation("org.apache.avro:avro:1.11.1")
}
