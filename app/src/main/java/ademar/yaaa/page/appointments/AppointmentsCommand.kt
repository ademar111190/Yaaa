package ademar.yaaa.page.appointments

sealed class AppointmentsCommand

object NavigateToAppointmentCreation : AppointmentsCommand()

data class NavigateToAppointmentDetails(
    val id: Long,
) : AppointmentsCommand()

object NavigateToLocations : AppointmentsCommand()
