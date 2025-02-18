// SPDX-FileCopyrightText: Contributors to the GXF project
//
// SPDX-License-Identifier: Apache-2.0

dependencies {
    implementation(project(":components:avro"))

    implementation(libs.springBootAutoconfigure)
    implementation(libs.logging)

    implementation(libs.springAop)

    implementation(libs.springKafka)
    implementation(libs.kafkaAvro)
    implementation(libs.kafkaAzureOAuth)

    testImplementation(libs.springTest)

    testImplementation(libs.junitJupiterApi)
    testImplementation(libs.junitJupiterEngine)
    testImplementation(libs.junitJupiterParams)
}
