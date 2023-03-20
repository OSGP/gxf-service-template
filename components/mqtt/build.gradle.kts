plugins {
    id("gxf.spring-conventions")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-logging")

    implementation("org.springframework.integration:spring-integration-mqtt")
    // Fixes: https://github.com/spring-projects/spring-integration/issues/3051
    implementation("org.springframework.integration:spring-integration-jmx")
}
