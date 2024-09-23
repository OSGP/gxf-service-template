// SPDX-FileCopyrightText: Contributors to the GXF project
//
// SPDX-License-Identifier: Apache-2.0

package org.gxf.servicetemplate.kafka.configuration

import com.gxf.utilities.kafka.avro.AvroDeserializer
import com.gxf.utilities.kafka.avro.AvroSerializer
import org.apache.avro.specific.SpecificRecordBase
import org.gxf.service.Measurement
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.ssl.SslBundles
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*


@Configuration
class KafkaConfiguration(val kafkaProperties: KafkaProperties, private val sslBundles: SslBundles) {

    @Bean
    fun kafkaListenerContainerFactory(consumerFactory: ConsumerFactory<String, SpecificRecordBase>): ConcurrentKafkaListenerContainerFactory<String, SpecificRecordBase> =
        ConcurrentKafkaListenerContainerFactory<String, SpecificRecordBase>()
            .apply { this.consumerFactory = consumerFactory }

    @Bean
    fun kafkaTemplate(producerFactory: ProducerFactory<String, SpecificRecordBase>) =
        KafkaTemplate(producerFactory)

    @Bean
    fun consumerFactory(): ConsumerFactory<String, SpecificRecordBase> =
        DefaultKafkaConsumerFactory(
            kafkaProperties.buildConsumerProperties(sslBundles),
            StringDeserializer(),
            AvroDeserializer(listOf(Measurement.getClassSchema()))
        )

    @Bean
    fun producerFactory(): ProducerFactory<String, SpecificRecordBase> =
        DefaultKafkaProducerFactory(
            kafkaProperties.buildProducerProperties(sslBundles),
            StringSerializer(),
            AvroSerializer()
        )
}
