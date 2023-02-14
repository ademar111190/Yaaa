package ademar.yaaa.usecase.mapper

import ademar.yaaa.data.Appointment
import ademar.yaaa.data.Location
import ademar.yaaa.db.appointment.AppointmentEntity
import org.junit.Before
import org.junit.Test
import java.time.Instant
import java.util.*
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

    private val date = Date.from(Instant.now())

    private fun makeAppointment() = Appointment(
        id = 1,
        location = makeLocation(),
        date = date,
        description = "description",
    )

    private fun makeAppointmentEntity() = AppointmentEntity(
        id = 1,
        locationId = 1,
        date = date,
        description = "description",
    )

    private fun makeLocation() = Location(
        id = 1,
        name = "name",
    )

}
