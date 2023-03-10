package ademar.yaaa.page.appointment

sealed class AppointmentModel

object Initial : AppointmentModel()

object Loading : AppointmentModel()

data class Error(
    val message: String,
) : AppointmentModel()

data class Success(
    val title: String,
    val hour: String,
    val date: String,
    val location: String?,
    val description: String,
    val locationOptions: Set<String>,
    val locationIndex: Int,
    val saveStatus: SaveStatus,
    val saveLabel: String,
    val deleteEnabled: Boolean,
) : AppointmentModel()

enum class SaveStatus {
    SAVED,
    SAVING,
    NOT_SAVED,
}
