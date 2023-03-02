package com.gxf.servicetemplate

import com.gxf.servicetemplate.jpa.MeasurementEntity
import com.gxf.servicetemplate.jpa.MeasurementRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.junit.jupiter.api.Assertions.*
import kotlin.random.Random


@DataJpaTest
class GxfServiceTemplateApplicationTests {

    @Autowired
    private lateinit var measurementRepository: MeasurementRepository

    @Test
    fun `test crud functionality`() {
        // Create
        val entity = MeasurementEntity()
        measurementRepository.save(entity)
        assertNotNull(entity.id)
        val randomValue: Double = Random.nextDouble()

        // Update
        entity.measurementValue = randomValue
        measurementRepository.save(entity)
        assertEquals(randomValue, entity.measurementValue)

        // Read
        val retrievedEntity = measurementRepository.findById(entity.id!!)

        assertTrue(retrievedEntity.isPresent)
        assertEquals(entity.measurementValue, randomValue)
        assertEquals(randomValue, retrievedEntity.get().measurementValue)

        // Delete
        measurementRepository.delete(entity)

        val deletedEntity = measurementRepository.findById(entity.id!!)

        assertTrue(deletedEntity.isEmpty)
    }

}
