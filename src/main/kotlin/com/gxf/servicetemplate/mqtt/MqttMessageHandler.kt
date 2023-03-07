package com.gxf.servicetemplate.mqtt

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.mqtt.core.MqttPahoClientFactory
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter
import org.springframework.messaging.Message
import org.springframework.stereotype.Service


@Service
class MqttMessageHandler(
    private val clientFactory: MqttPahoClientFactory
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }

    // Test with: mosquitto_pub -h localhost -t topic1 -m 23 -u admin -P password
    @Bean
    fun mqtt(): IntegrationFlow {
        return IntegrationFlow.from(
            MqttPahoMessageDrivenChannelAdapter("gxf-listener", clientFactory, "measurement")
        )
            .handle { m: Message<*> -> processMqttMessage(m) }
            .get()
    }

    private fun processMqttMessage(m: Message<*>) {
        logger.info("mqtt: ${m.headers.timestamp} - ${m.payload}")
    }
}
