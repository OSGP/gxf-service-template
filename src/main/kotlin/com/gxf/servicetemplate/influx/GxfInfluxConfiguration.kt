package com.gxf.servicetemplate.influx

import com.influxdb.client.InfluxDBClientFactory
import com.influxdb.client.WriteApiBlocking
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GxfInfluxConfiguration {

    @Bean
    fun influxWriteApi(): WriteApiBlocking {
        val influxDBClient = InfluxDBClientFactory
            .create("http://localhost:8086", "my-token".toCharArray(), "my-org", "influx")

       return influxDBClient.writeApiBlocking
    }

}
