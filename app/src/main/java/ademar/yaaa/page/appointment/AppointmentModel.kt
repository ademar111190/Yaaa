package ademar.yaaa.page.appointment

sealed class AppointmentModel

object Initial : AppointmentModel()

data class Success(
    val date: String,
    val location: String,
    val description: String,
) : AppointmentModel()
