// SPDX-FileCopyrightText: Contributors to the GXF project
//
// SPDX-License-Identifier: Apache-2.0

rootProject.name = "gxf-service-template"

include("application")
include("components:avro")
include("components:kafka")
include("components:mqtt")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("logging", "io.github.oshai", "kotlin-logging-jvm").version("6.0.1")

            version("mapstruct", "1.5.5.Final")
            library("mapstruct", "org.mapstruct", "mapstruct").versionRef("mapstruct")
            library("mapstructannotation", "org.mapstruct", "mapstruct-processor").versionRef("mapstruct")

            library("avro", "org.apache.avro", "avro").version("1.11.3")

            version("gxfUtils", "0.2")
            library("kafkaAvro", "com.gxf.utilities", "kafka-avro").versionRef("gxfUtils")
            library("kafkaAzureOAuth", "com.gxf.utilities", "kafka-azure-oauth").versionRef("gxfUtils")
        }
        create("integrationTestLibs") {
            version("testContainers", "1.19.3")
            library("kafkaTestContainers", "org.testcontainers", "kafka").versionRef("testContainers")

        }
    }
}
