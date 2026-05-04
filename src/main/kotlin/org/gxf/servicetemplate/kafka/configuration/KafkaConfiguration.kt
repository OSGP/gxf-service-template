// SPDX-FileCopyrightText: Copyright Contributors to the GXF project
//
// SPDX-License-Identifier: Apache-2.0
package org.gxf.servicetemplate.kafka.configuration

import com.gxf.utilities.kafka.avro.AvroDeserializer
import com.gxf.utilities.kafka.avro.AvroSerializer
import org.apache.avro.specific.SpecificRecordBase
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.gxf.service.Measurement
import org.springframework.boot.kafka.autoconfigure.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer

@Configuration
class KafkaConfiguration(private val kafkaProperties: KafkaProperties) {
    @Bean
    fun kafkaListenerContainerFactory(
        consumerFactory: ConsumerFactory<String, SpecificRecordBase>,
    ): ConcurrentKafkaListenerContainerFactory<String, SpecificRecordBase> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, SpecificRecordBase>()
        factory.setConsumerFactory(consumerFactory)
        return factory
    }

    @Bean
    fun kafkaTemplate(producerFactory: ProducerFactory<String, SpecificRecordBase>) = KafkaTemplate(producerFactory)

    @Bean
    fun consumerFactory(): ConsumerFactory<String, SpecificRecordBase> = DefaultKafkaConsumerFactory(
        kafkaProperties.buildConsumerProperties(),
        ErrorHandlingDeserializer(StringDeserializer()),
        ErrorHandlingDeserializer(
            AvroDeserializer(listOf(Measurement.getClassSchema())),
        ),
    )

    @Bean
    fun producerFactory(): ProducerFactory<String, SpecificRecordBase> = DefaultKafkaProducerFactory(
        kafkaProperties.buildProducerProperties(),
        StringSerializer(),
        AvroSerializer(),
    )
}
