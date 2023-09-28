/*
SPDX-FileCopyrightText: Contributors to the GXF project

SPDX-License-Identifier: Apache-2.0
*/
package com.gxf.servicetemplate.kafka.configuration

import org.apache.avro.message.BinaryMessageDecoder
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer
import org.slf4j.LoggerFactory

class AvroDeserializer<T : SpecificRecord>(private val decoder: BinaryMessageDecoder<T>) : Deserializer<T> {

    /**
     * Deserializes a Byte Array to an Avro specific record
     */
    override fun deserialize(topic: String, payload: ByteArray): T? {
        try {
            return decoder.decode(payload)
        } catch (ex: Exception) {
            throw SerializationException("Error deserializing Avro message for topic: ${topic}", ex)
        }
    }
}

