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
            library("logging", "io.github.oshai", "kotlin-logging-jvm").version("7.0.0")

            version("mapstruct", "1.5.5.Final")
            library("mapstruct", "org.mapstruct", "mapstruct").versionRef("mapstruct")
            library("mapstructannotation", "org.mapstruct", "mapstruct-processor").versionRef("mapstruct")

            library("avro", "org.apache.avro", "avro").version("1.12.0")

            version("gxfUtils", "2.0")
            library("kafkaAvro", "com.gxf.utilities", "kafka-avro").versionRef("gxfUtils")
            library("kafkaAzureOAuth", "com.gxf.utilities", "kafka-azure-oauth").versionRef("gxfUtils")
        }
    }
}
