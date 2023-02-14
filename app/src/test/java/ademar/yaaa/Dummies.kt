package ademar.yaaa

import ademar.yaaa.data.Appointment
import ademar.yaaa.data.Location
import ademar.yaaa.db.appointment.AppointmentEntity
import ademar.yaaa.db.location.LocationEntity
import java.time.Instant
import java.util.*

object Dummies {

    fun initialLocations() = listOf(
        Location(1, "San Diego"),
        Location(2, "St. George"),
        Location(3, "Park City"),
        Location(4, "Dallas"),
        Location(5, "Memphis"),
        Location(6, "Orlando"),
    )

    fun initialLocationEntities() = arrayOf(
        LocationEntity(1, "San Diego", false),
        LocationEntity(2, "St. George", false),
        LocationEntity(3, "Park City", false),
        LocationEntity(4, "Dallas", false),
        LocationEntity(5, "Memphis", false),
        LocationEntity(6, "Orlando", false),
    )

    fun makeLocation() = Location(
        id = 1,
        name = "A Name",
    )

    fun makeLocationEntity() = LocationEntity(
        id = 1,
        name = "A Name",
        deleted = false,
    )

    private val date = Date.from(Instant.now())

    fun makeAppointment() = Appointment(
        id = 1,
        location = makeLocation(),
        date = date,
        description = "A Description",
    )

    fun makeAppointmentEntity(
        id: Long = 1,
        locationId: Long = 1,
    ) = AppointmentEntity(id, locationId, date, "A Description")

}
