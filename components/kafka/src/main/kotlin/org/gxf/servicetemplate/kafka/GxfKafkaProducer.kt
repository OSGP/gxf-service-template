// SPDX-FileCopyrightText: Copyright Contributors to the GXF project
//
// SPDX-License-Identifier: Apache-2.0
package org.gxf.servicetemplate.kafka

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.random.Random
import org.apache.avro.specific.SpecificRecordBase
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class GxfKafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, SpecificRecordBase>,
    private val measurementGenerator: MeasurementGenerator,
) {

    private val logger = KotlinLogging.logger {}

    @Scheduled(cron = "* * * * * *")
    fun producer() {
        val deviceId = Random.nextLong()
        logger.info { "Producing: ${deviceId}" }
        kafkaTemplate.send("avroTopic", measurementGenerator.generateMeasurement(deviceId))
    }
}
