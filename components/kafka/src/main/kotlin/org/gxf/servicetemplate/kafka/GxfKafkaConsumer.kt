// SPDX-FileCopyrightText: Contributors to the GXF project
//
// SPDX-License-Identifier: Apache-2.0

package org.gxf.servicetemplate.kafka

import org.gxf.service.Measurement
import io.micrometer.observation.annotation.Observed
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.retry.annotation.Backoff
import org.springframework.stereotype.Service
import java.net.SocketTimeoutException
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class GxfKafkaConsumer {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Observed(name = "consumer.consumed")
    @KafkaListener(topics = ["avroTopic"], id = "gxf-kafka-consumer")
    @RetryableTopic(
        backoff = Backoff(value = 3000L),
        attempts = "2",
        include = [SocketTimeoutException::class]
    )
    fun consume(record: ConsumerRecord<String, Measurement>) {
        logger.info("Consuming: ${record.value().deviceId}")
        if ((LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) % 2) == 0L) {
            logger.info("Timeout!: ${record.value().deviceId}")
            throw SocketTimeoutException()
        }
    }
}
