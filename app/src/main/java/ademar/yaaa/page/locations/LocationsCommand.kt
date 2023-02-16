package ademar.yaaa.page.locations

sealed class LocationsCommand

object NavigateBack : LocationsCommand()

data class NavigateToAddLocation(
    val name: String,
) : LocationsCommand()

data class NavigateToEditLocation(
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

data class AnnounceDeleteError(
    val message: String,
) : LocationsCommand()

data class AnnounceDeleteSuccess(
    val message: String,
    val action: String,
) : LocationsCommand()

data class AnnounceUndoResult(
    val message: String,
) : LocationsCommand()

data class AnnounceEditionError(
    val message: String,
) : LocationsCommand()

data class AnnounceEditionSuccess(
    val message: String,
) : LocationsCommand()
