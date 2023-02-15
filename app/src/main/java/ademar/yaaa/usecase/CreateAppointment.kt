package ademar.yaaa.usecase

import ademar.yaaa.data.Appointment
import ademar.yaaa.data.Location
import ademar.yaaa.db.AppDatabase
import ademar.yaaa.db.appointment.AppointmentEntity
import ademar.yaaa.usecase.mapper.AppointmentMapper
import dagger.Reusable
import java.util.*
import javax.inject.Inject

@Reusable
class CreateAppointment @Inject constructor(
    private val appDatabase: AppDatabase,
    private val appointmentMapper: AppointmentMapper,
) {

    suspend fun createAppointment(
        date: Date,
        location: Location,
        description: String,
    ): Appointment {
        val rowId = appDatabase.appointmentDao().create(
            AppointmentEntity(
                locationId = location.id,
                date = date,
                description = description,
            )
        ) ?: throw IllegalStateException("Failed to save appointment.")
        val pk = appDatabase.appointmentDao().readPk(rowId)
            ?: throw IllegalStateException("Failed to save appointment.")
        val entity = appDatabase.appointmentDao()
            .read(pk) ?: throw IllegalStateException("Failed to save appointment.")
        return appointmentMapper.mapToAppointment(entity, location)
    }

}
