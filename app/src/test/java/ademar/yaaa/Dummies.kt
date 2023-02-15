package ademar.yaaa

import ademar.yaaa.data.Appointment
import ademar.yaaa.data.Location
import ademar.yaaa.db.appointment.AppointmentEntity
import ademar.yaaa.db.location.LocationEntity
import java.time.Instant
import java.util.*

object Dummies {

    val firstLocation = Location(1, "San Diego")

    fun initialLocations() = listOf(
        Location(4, "Dallas"),
        Location(5, "Memphis"),
        Location(6, "Orlando"),
        Location(3, "Park City"),
        firstLocation,
        Location(2, "St. George"),
    )

    val firstLocationEntity = LocationEntity(1, "San Diego", false)

    fun initialLocationEntities() = arrayOf(
        LocationEntity(4, "Dallas", false),
        LocationEntity(5, "Memphis", false),
        LocationEntity(6, "Orlando", false),
        LocationEntity(3, "Park City", false),
        firstLocationEntity,
        LocationEntity(2, "St. George", false),
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
