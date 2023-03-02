package com.gxf.servicetemplate.jpa

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class MeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null

    var messageId: String? = null
    var producerId: Long? = null
    var name: String? = null
}
