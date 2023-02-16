package ademar.yaaa.page.locations

sealed class LocationsCommand

object NavigateBack : LocationsCommand()

data class NavigateToAddLocation(
    val name: String,
) : LocationsCommand()

data class AnnounceSaveError(
    val message: String,
    val action: String,
) : LocationsCommand()

data class AnnounceSaveSuccess(
    val message: String,
    val action: String,
) : LocationsCommand()
