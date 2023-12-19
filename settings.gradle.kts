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
            version("gxfUtils", "0.2")
            library("kafkaAvro", "com.gxf.utilities", "kafka-avro").versionRef("gxfUtils")
            library("kafkaAzureOAuth", "com.gxf.utilities", "kafka-azure-oauth").versionRef("gxfUtils")
        }
    }
}
