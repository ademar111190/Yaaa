package ademar.yaaa.page.appointment

sealed class AppointmentModel

object Initial : AppointmentModel()

object Loading : AppointmentModel()

data class Error(
    val message: String,
) : AppointmentModel()

data class Success(
    val date: String,
    val location: String?,
    val description: String,
    val locationOptions: Set<String>,
) : AppointmentModel()
