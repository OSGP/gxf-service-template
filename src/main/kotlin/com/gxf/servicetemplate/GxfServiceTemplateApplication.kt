package com.gxf.servicetemplate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.integration.config.EnableIntegration
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableIntegration
@SpringBootApplication
class GxfServiceTemplateApplication

fun main(args: Array<String>) {
    runApplication<GxfServiceTemplateApplication>(*args)
}
