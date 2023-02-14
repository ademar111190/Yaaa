package ademar.yaaa.usecase

import ademar.yaaa.db.AppDatabase
import ademar.yaaa.usecase.mapper.AppointmentMapper
import dagger.Reusable
import javax.inject.Inject

@Reusable
class FetchAppointments @Inject constructor(
    private val appDatabase: AppDatabase,
    private val fetchLocations: FetchLocations,
    private val appointmentMapper: AppointmentMapper,
) {

    suspend fun allAppointments() = appDatabase.appointmentDao().readAll()
        .map {
            appointmentMapper.mapToAppointment(it, fetchLocations.locationById(it.locationId))
        }

}
