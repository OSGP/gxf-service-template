package com.gxf.servicetemplate.kafka

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import kotlin.random.Random


@Service
class GxfKafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    private val measurementGenerator: MeasurementGenerator
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Scheduled(cron = "* * * * * *")
    fun producer() {
        val deviceId = Random.nextLong()
        logger.info("Producing: ${deviceId}")
        kafkaTemplate.send("avroTopic", measurementGenerator.generateMeasurement(deviceId))
    }
}