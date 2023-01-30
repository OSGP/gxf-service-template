package com.gxf.servicetemplate.kafka

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

    @KafkaListener(topics = ["topic"])
    fun listen(record: ConsumerRecord<String, String>) {
        logger.info("Receiving: ${record.value()}")
    }
}
