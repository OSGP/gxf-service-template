package com.gxf.servicetemplate.kafka

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

class MeasurementGeneratorTest {

    @Test
    fun generateMeasurement() {
        val measurementGenerator = MeasurementGenerator()

        val measurement = measurementGenerator.generateMeasurement(1)
        assertEquals(1, measurement.deviceId)

    }
}
