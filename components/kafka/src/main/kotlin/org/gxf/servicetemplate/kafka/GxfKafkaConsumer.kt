// SPDX-FileCopyrightText: Contributors to the GXF project
//
// SPDX-License-Identifier: Apache-2.0

package org.gxf.servicetemplate.kafka

import io.github.oshai.kotlinlogging.KotlinLogging
import org.gxf.service.Measurement
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.net.SocketTimeoutException
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class GxfKafkaConsumer {

    private val logger = KotlinLogging.logger { }

    @KafkaListener(topics = ["avroTopic"], id = "gxf-kafka-consumer")
    fun consume(record: ConsumerRecord<String, Measurement>) {
        logger.info { "Consuming: ${record.value().deviceId}" }
        if ((LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) % 2) == 0L) {
            logger.info { "Timeout!: ${record.value().deviceId}" }
            throw SocketTimeoutException()
        }
    }
}
