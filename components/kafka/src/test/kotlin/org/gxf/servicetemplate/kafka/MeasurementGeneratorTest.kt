// SPDX-FileCopyrightText: Contributors to the GXF project
//
// SPDX-License-Identifier: Apache-2.0

package org.gxf.servicetemplate.kafka

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MeasurementGeneratorTest {

    @Test
    fun generateMeasurement() {
        val measurementGenerator = MeasurementGenerator()

        val measurement = measurementGenerator.generateMeasurement(1)
        assertEquals(1, measurement.deviceId)

    }
}
