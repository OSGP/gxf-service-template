package com.gxf.servicetemplate.kafka

import com.gxf.service.Measurement
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import io.confluent.kafka.serializers.KafkaAvroSerializer
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.IntegerDeserializer
import org.apache.kafka.common.serialization.IntegerSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer


@Configuration
class KafkaConfiguration {


    @Value("\${kafka.bootstrap-servers}")
    lateinit var bootstrapServers: String

    @Value("\${kafka.schema.registry.url}")
    lateinit var schemaRegistryUrl: String

    @Bean
    fun kafkaListenerContainerFactory(consumerFactory: ConsumerFactory<String, Measurement>): ConcurrentKafkaListenerContainerFactory<String, Measurement> {
        val factory: ConcurrentKafkaListenerContainerFactory<String, Measurement> =
            ConcurrentKafkaListenerContainerFactory<String, Measurement>()
        factory.consumerFactory = consumerFactory
        return factory
    }

    @Bean
    fun kafkaTemplate(producerFactory: ProducerFactory<String, Measurement>): KafkaTemplate<String, Measurement> {
        return KafkaTemplate(producerFactory)
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Measurement> {
        val config: MutableMap<String, Any> = HashMap()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        config[ConsumerConfig.GROUP_ID_CONFIG] = "gxf-service-template"
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer::class.java
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = ErrorHandlingDeserializer::class.java
        config[ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS] = IntegerDeserializer::class.java
        config[ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS] = KafkaAvroDeserializer::class.java
        config[KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = schemaRegistryUrl
        config[KafkaAvroDeserializerConfig.AUTO_REGISTER_SCHEMAS] = true
        config[KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG] = true
        return DefaultKafkaConsumerFactory(config)
    }

    @Bean
    fun producerFactory(): ProducerFactory<String, Measurement> {
        val config: MutableMap<String, Any> = HashMap()
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = IntegerSerializer::class.java
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaAvroSerializer::class.java
        config[KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = schemaRegistryUrl
        config[KafkaAvroSerializerConfig.AUTO_REGISTER_SCHEMAS] = true
        return DefaultKafkaProducerFactory(config)
    }
}
