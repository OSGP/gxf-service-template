// SPDX-FileCopyrightText: Contributors to the GXF project
//
// SPDX-License-Identifier: Apache-2.0

plugins {
    id("gxf.spring-conventions")
}

dependencies {
    implementation(project(":components:avro"))

    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-starter-logging")

    implementation("org.springframework:spring-aspects")
    implementation("org.springframework:spring-aop")

    implementation("org.springframework.kafka:spring-kafka")
    implementation("io.confluent:kafka-avro-serializer:7.3.0")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}
