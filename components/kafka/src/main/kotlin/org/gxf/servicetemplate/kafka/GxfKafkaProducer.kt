// SPDX-FileCopyrightText: Contributors to the GXF project
//
// SPDX-License-Identifier: Apache-2.0

package org.gxf.servicetemplate.kafka

import io.github.oshai.kotlinlogging.KotlinLogging
import org.gxf.service.Measurement
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import kotlin.random.Random


@Service
class GxfKafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, Measurement>,
    private val measurementGenerator: MeasurementGenerator
) {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    @Scheduled(cron = "* * * * * *")
    fun producer() {
        val deviceId = Random.nextLong()
        logger.info { "Producing: ${deviceId}" }
        kafkaTemplate.send("avroTopic", measurementGenerator.generateMeasurement(deviceId))
    }
}
