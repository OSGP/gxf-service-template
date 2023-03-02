package com.gxf.servicetemplate.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface MeasurementRepository: JpaRepository<MeasurementEntity, String> {

}
