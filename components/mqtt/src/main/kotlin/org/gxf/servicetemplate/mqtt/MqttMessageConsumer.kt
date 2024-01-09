// SPDX-FileCopyrightText: Contributors to the GXF project
//
// SPDX-License-Identifier: Apache-2.0

package org.gxf.servicetemplate.mqtt

import io.github.oshai.kotlinlogging.KotlinLogging
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

    private val logger = KotlinLogging.logger { }

    // Test with: mosquitto_pub -h localhost -t topic1 -m 23 -u admin -P password
    @Bean
    fun messageSourceFlow(): IntegrationFlow =
        integrationFlow(MqttPahoMessageDrivenChannelAdapter("gxf-listener", clientFactory, "measurement")) {
            handle { message: Message<*> -> processMqttMessage(message) }
        }


    private fun processMqttMessage(m: Message<*>) {
        logger.info { "mqtt: ${m.headers.timestamp} - ${m.payload}" }
    }
}
