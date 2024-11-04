// SPDX-FileCopyrightText: Copyright Contributors to the GXF project
//
// SPDX-License-Identifier: Apache-2.0
package org.gxf.servicetemplate.kafka

import java.time.Instant
import kotlin.random.Random
import org.gxf.service.Measurement
import org.gxf.service.VoltageMeasurement
import org.springframework.stereotype.Service

@Service
class MeasurementGenerator {
    fun generateMeasurement(deviceId: Long): Measurement {
        return Measurement(
            Instant.now().toEpochMilli(),
            deviceId,
            listOf(VoltageMeasurement(Random.nextDouble(), "one"))
        )
    }
}
