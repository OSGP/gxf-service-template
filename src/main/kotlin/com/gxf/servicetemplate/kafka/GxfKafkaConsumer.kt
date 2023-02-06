package com.gxf.servicetemplate.kafka

import com.gxf.service.Measurement
import io.micrometer.observation.annotation.Observed
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class GxfKafkaConsumer {

    companion object {
        val logger: Logger = LoggerFactory.getLogger("GxfKafkaConsumer")
    }

    @KafkaListener(topics = ["avroTopic"])
    @Observed(name = "consumer.consumed")
    fun consume(record: ConsumerRecord<String, Measurement>) {
        logger.trace("Receiving: ${record.value().device.deviceId}")
    }
}
