package com.gxf.servicetemplate.influx

import com.gxf.service.Measurement
import com.influxdb.client.WriteApiBlocking
import com.influxdb.client.domain.WritePrecision
import com.influxdb.client.write.Point
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*

@Service
class GxfInfluxService(val writeApi: WriteApiBlocking) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger("GxfInfluxService")
    }

    val points: MutableList<Point> = mutableListOf()

    fun postMeasurement(measurement: Measurement) {
        val point = Point("lsm_measurement")
            .time(measurement.timestamp, WritePrecision.NS)
            .addField("values", measurement.voltageMeasurements.first().value)
            .addTag("test", UUID.randomUUID().toString())

        synchronized(points) {
            points.add(point)
        }
    }


    @Scheduled(cron = "*/10 * * * * *")
    fun insertIntoInflux() {
        logger.info("Inserting into influx")
        synchronized(points) {
            writeApi.writePoints(points)
            points.clear()
        }
    }
}
