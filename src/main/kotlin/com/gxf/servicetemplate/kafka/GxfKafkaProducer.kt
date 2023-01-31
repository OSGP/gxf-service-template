package com.gxf.servicetemplate.kafka

import com.gxf.service.Device
import com.gxf.service.DeviceType
import com.gxf.service.Measurement
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random


@Service
class GxfKafkaProducer(val kafkaTemplate: KafkaTemplate<String, Measurement>,) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger("Producer")
    }

    @Scheduled(cron = "* * * * * *")
    fun producer() {
        logger.info("Producing: ${DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now())}")
        (1000..2000).forEach {
            val message = Measurement(Device(it, DeviceType.LIGHT_SENSOR), Random.nextDouble())
            kafkaTemplate.send("avroTopic", message)
        }
        logger.info("Produced: ${DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now())}")
    }
}
