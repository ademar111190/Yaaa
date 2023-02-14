package ademar.yaaa.usecase.mapper

import ademar.yaaa.data.Appointment
import ademar.yaaa.data.Location
import ademar.yaaa.db.appointment.AppointmentEntity
import dagger.Reusable
import javax.inject.Inject

@Reusable
class AppointmentMapper @Inject constructor() {

    fun mapToAppointmentEntity(
        appointment: Appointment,
    ) = AppointmentEntity(
        id = appointment.id,
        locationId = appointment.location.id,
        date = appointment.date,
        description = appointment.description,
    )

    fun mapToAppointment(
        appointmentEntity: AppointmentEntity,
        location: Location,
    ) = Appointment(
        id = appointmentEntity.id,
        location = location,
        date = appointmentEntity.date,
        description = appointmentEntity.description,
    )

}
