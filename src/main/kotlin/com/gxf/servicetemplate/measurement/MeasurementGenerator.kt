package com.gxf.servicetemplate.measurement

import com.gxf.service.Measurement
import com.gxf.service.VoltageMeasurement
import org.springframework.stereotype.Service
import java.time.Instant
import kotlin.random.Random

@Service
class MeasurementGenerator {

    fun generateMeasurement(deviceId: Long): Measurement {
        return Measurement(Instant.now().toEpochMilli(), deviceId, listOf(VoltageMeasurement(Random.nextDouble(), "one")))
    }

}
