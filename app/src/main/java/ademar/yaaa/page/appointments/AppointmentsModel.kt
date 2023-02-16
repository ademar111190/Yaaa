package ademar.yaaa.page.appointments

sealed class AppointmentsModel

object Initial : AppointmentsModel()

object Loading : AppointmentsModel()

data class Error(
    val message: String,
) : AppointmentsModel()

data class Success(
    val appointments: List<AppointmentItem>,
) : AppointmentsModel()

data class AppointmentItem(
    val id: Long,
    val hour: String,
    val date: String,
    val location: String,
    val description: String,
)
