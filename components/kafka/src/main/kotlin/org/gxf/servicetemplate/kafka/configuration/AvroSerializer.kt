package org.gxf.servicetemplate.kafka.configuration

import org.apache.avro.message.BinaryMessageEncoder
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Serializer

class AvroSerializer<T : SpecificRecord>(private val decoder: BinaryMessageEncoder<T>) : Serializer<T> {

    /**
     * Serializes a Byte Array to an Avro specific record
     */
    override fun serialize(topic: String?, data: T): ByteArray {
        try {
            return decoder.encode(data).array()
        } catch (ex: Exception) {
            throw SerializationException("Error serializing Avro message for topic: ${topic}", ex)
        }
    }
}

