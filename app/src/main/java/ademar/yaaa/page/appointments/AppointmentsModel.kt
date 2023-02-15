package ademar.yaaa.page.appointments

import ademar.yaaa.data.Appointment

sealed class AppointmentsModel

object Initial : AppointmentsModel()

object Loading : AppointmentsModel()

data class Error(
    val message: String,
) : AppointmentsModel()

data class Success(
    val appointments: List<Appointment>,
) : AppointmentsModel()
