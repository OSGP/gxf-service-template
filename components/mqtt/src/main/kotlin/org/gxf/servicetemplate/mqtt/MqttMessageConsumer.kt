// SPDX-FileCopyrightText: Contributors to the GXF project
//
// SPDX-License-Identifier: Apache-2.0

package org.gxf.servicetemplate.mqtt

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.integrationFlow
import org.springframework.integration.mqtt.core.MqttPahoClientFactory
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter
import org.springframework.messaging.Message
import org.springframework.stereotype.Service


@Service
class MqttMessageConsumer(
    private val clientFactory: MqttPahoClientFactory
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }

    // Test with: mosquitto_pub -h localhost -t topic1 -m 23 -u admin -P password
    @Bean
    fun messageSourceFlow(): IntegrationFlow =
        integrationFlow(MqttPahoMessageDrivenChannelAdapter("gxf-listener", clientFactory, "measurement" )) {
            handle { message: Message<*> -> processMqttMessage(message) }
        }


    private fun processMqttMessage(m: Message<*>) {
        logger.info("mqtt: ${m.headers.timestamp} - ${m.payload}")
    }
}
