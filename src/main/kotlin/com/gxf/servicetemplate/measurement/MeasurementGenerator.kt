package com.gxf.servicetemplate.measurement

import com.gxf.service.Measurement
import com.gxf.service.VoltageMeasurement
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class MeasurementGenerator {

    fun generateMeasurement(deviceId: Long, voltage: Double): Measurement {
        return Measurement(Instant.now().toEpochMilli(), deviceId, listOf(VoltageMeasurement(voltage, "one")))
    }

}
