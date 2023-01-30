package com.gxf.servicetemplate.kafka

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Service
class GxfKafkaProducer(val kafkaTemplate: KafkaTemplate<String, String>,) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger("Producer")
    }

    @Scheduled(cron = "* * * * * *")
    fun producer() {
        val message = "time: ${DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now())}"
        logger.info("Producing: ${message}")
        kafkaTemplate.send("topic", message)
    }
}
