// SPDX-FileCopyrightText: Contributors to the GXF project
//
// SPDX-License-Identifier: Apache-2.0

plugins { alias(libs.plugins.springBoot) }

dependencies {
    implementation(libs.springBootStarterActuator)
    implementation(libs.springBootStarterWeb)

    implementation(project(":components:kafka"))
    implementation(project(":components:mqtt"))

    implementation(libs.springAspects)

    runtimeOnly(libs.micrometerPrometheusModule)

    // Generate test and integration test reports
    jacocoAggregation(project(":application"))
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootBuildImage> {
    imageName.set("ghcr.io/osgp/gxf-service-template:${version}")
    if (project.hasProperty("publishImage")) {
        publish.set(true)
        docker {
            publishRegistry {
                username.set(System.getenv("GITHUB_ACTOR"))
                password.set(System.getenv("GITHUB_TOKEN"))
            }
        }
    }
}

testing {
    suites {
        val integrationTest by
            registering(JvmTestSuite::class) {
                useJUnitJupiter()
                dependencies {
                    implementation(project())
                    implementation(libs.springBootStarterTest)
                    implementation(libs.springKafkaTest)
                    implementation(libs.kafkaTestContainer)
                }
            }
    }
}
