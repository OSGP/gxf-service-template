package com.gxf.servicetemplate.influx

import com.gxf.service.Measurement
import com.influxdb.client.WriteApiBlocking
import com.influxdb.client.domain.WritePrecision
import com.influxdb.client.write.Point
import org.springframework.stereotype.Service
import java.util.*

@Service
class GxfInfluxService(val writeApi: WriteApiBlocking) {
    fun postMeasurement(measurement: Measurement) {
        val point = Point("lsm_measurement")
            .time(measurement.timestamp, WritePrecision.NS)
            .addField("values", measurement.voltageMeasurements.first().value)
            .addTag("test", UUID.randomUUID().toString())
            writeApi.writePoint(point)
    }
}
