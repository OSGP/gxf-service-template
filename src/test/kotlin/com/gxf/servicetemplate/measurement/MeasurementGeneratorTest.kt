package com.gxf.servicetemplate.measurement

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import kotlin.random.Random

class MeasurementGeneratorTest {

    @Test
    fun generateMeasurement() {
        val measurementGenerator = MeasurementGenerator()

        val measurement = measurementGenerator.generateMeasurement(1, Random.nextDouble())
        assertEquals(1, measurement.deviceId)

    }
}
