// SPDX-FileCopyrightText: Contributors to the GXF project
//
// SPDX-License-Identifier: Apache-2.0

package org.gxf.servicetemplate.mqtt

import org.springframework.context.annotation.Bean
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.Pollers.cron
import org.springframework.integration.dsl.integrationFlow
import org.springframework.integration.mqtt.core.MqttPahoClientFactory
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler
import org.springframework.stereotype.Service
import kotlin.random.Random


@Service
class MqttMessageProducer(
    private val clientFactory: MqttPahoClientFactory
) {

    fun messageHandler(): MqttPahoMessageHandler {
        val messageHandler = MqttPahoMessageHandler("gxf-publisher", clientFactory)
        messageHandler.setDefaultTopic("measurement")
        return messageHandler
    }


    @Bean
    fun mqttOutboundFlow(): IntegrationFlow {
        return integrationFlow(
            { Random.nextDouble().toString() },
            { poller { cron("* * * * * *") } }
        ) {
            handle(messageHandler())
        }
    }
}
