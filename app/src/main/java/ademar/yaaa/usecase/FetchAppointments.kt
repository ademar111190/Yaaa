package ademar.yaaa.usecase

import ademar.yaaa.data.Appointment
import ademar.yaaa.db.AppDatabase
import ademar.yaaa.usecase.mapper.AppointmentMapper
import dagger.Reusable
import org.slf4j.LoggerFactory
import javax.inject.Inject

@Reusable
class FetchAppointments @Inject constructor(
    private val appDatabase: AppDatabase,
    private val fetchLocations: FetchLocations,
    private val appointmentMapper: AppointmentMapper,
) {

    private val log = LoggerFactory.getLogger("FetchAppointments")

    suspend fun allAppointments() = appDatabase.appointmentDao().readAll()
        .map {
            appointmentMapper.mapToAppointment(it, fetchLocations.locationById(it.locationId))
        }

    suspend fun appointmentById(id: Long): Appointment? {
        val entity = appDatabase.appointmentDao().read(id)
        if (entity != null) {
            return appointmentMapper.mapToAppointment(entity, fetchLocations.locationById(entity.locationId))
        }
        log.error("Appointment with id $id not found")
        return null
    }

}
