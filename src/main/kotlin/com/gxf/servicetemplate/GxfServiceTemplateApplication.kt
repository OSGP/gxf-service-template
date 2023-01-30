package com.gxf.servicetemplate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class GxfServiceTemplateApplication

fun main(args: Array<String>) {
    runApplication<GxfServiceTemplateApplication>(*args)
}
