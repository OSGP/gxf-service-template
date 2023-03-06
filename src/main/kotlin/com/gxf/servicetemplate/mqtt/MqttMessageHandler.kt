package com.gxf.servicetemplate.mqtt

import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory
import org.springframework.integration.mqtt.core.MqttPahoClientFactory
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter
import org.springframework.messaging.Message
import org.springframework.stereotype.Service


@Service
class MqttMessageHandler {

    companion object {
        val logger: Logger = LoggerFactory.getLogger("MqttMessageHandler")
    }

    private fun mqttClientFactory(): MqttPahoClientFactory {
        val factory = DefaultMqttPahoClientFactory()
        val options = MqttConnectOptions()
        options.serverURIs = arrayOf("tcp://localhost:1883")
        options.userName = "admin"
        options.password = "password".toCharArray()
        factory.connectionOptions = options
        return factory
    }

    // Test with: mosquitto_pub -h localhost -t topic1 -m 23 -u admin -P password
    @Bean
    fun mqtt(): IntegrationFlow {
        return IntegrationFlow.from(
            MqttPahoMessageDrivenChannelAdapter("testClient", mqttClientFactory(), "topic1", "topic2")
        )
            .handle { m: Message<*> -> processMqttMessage(m) }
            .get()
    }

    private fun processMqttMessage(m: Message<*>) {
        logger.info("mqtt: ${m.headers.timestamp} - ${m.payload}")
    }
}
