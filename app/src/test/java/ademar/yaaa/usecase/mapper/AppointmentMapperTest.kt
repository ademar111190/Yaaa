package ademar.yaaa.usecase.mapper

import ademar.yaaa.Dummies.makeAppointment
import ademar.yaaa.Dummies.makeAppointmentEntity
import ademar.yaaa.Dummies.makeLocation
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class AppointmentMapperTest {

    private lateinit var mapper: AppointmentMapper

    @Before
    fun setUp() {
        mapper = AppointmentMapper()
    }

    @Test
    fun mapToAppointmentEntity() {
        val appointment = makeAppointment()

        val appointmentEntity = mapper.mapToAppointmentEntity(appointment)

        assertEquals(appointmentEntity, makeAppointmentEntity())
    }

    @Test
    fun mapToAppointment() {
        val appointmentEntity = makeAppointmentEntity()

        val appointment = mapper.mapToAppointment(appointmentEntity, makeLocation())

        assertEquals(appointment, makeAppointment())
    }

}
