package ademar.yaaa.page.locations

import ademar.yaaa.R
import ademar.yaaa.data.Location
import ademar.yaaa.usecase.CreateLocation
import ademar.yaaa.usecase.FetchLocations
import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    application: Application,
    private val resources: Resources,
    private val fetchLocations: FetchLocations,
    private val createLocation: CreateLocation,
) : AndroidViewModel(application) {

    private val log = LoggerFactory.getLogger("LocationsViewModel")
    val model = MutableLiveData<LocationsModel>(Initial)
    val command = MutableLiveData<LocationsCommand>()

    private var locationsById = mutableMapOf<Long, Location>()
    private var lastTriedLocationName = ""
    private var deletedId: Long? = null
    private var editedId: Long? = null

    fun load() = viewModelScope.launch {
        log.debug("load")
        model.value = Loading

        try {
            val locations = fetchLocations.allLocations()
            locations.forEach { location ->
                locationsById[location.id] = location
            }
        } catch (e: Exception) {
            log.error("Error fetching locations", e)
            model.value = Error(resources.getString(R.string.locations_error_fetching_locations))
            return@launch
        }

        model.value = success()
    }

    fun add() {
        log.debug("add")
        command.value = NavigateToAddLocation("")
    }

    fun addLocation(name: String) = viewModelScope.launch {
        log.debug("addLocation: $name")
        lastTriedLocationName = name

        if (name.isBlank()) {
            log.error("addLocation: name is blank")
            command.value = AnnounceSaveError(
                message = resources.getString(R.string.add_location_error_invalid_name),
                action = resources.getString(R.string.add_location_saved_failed_action),
            )
            return@launch
        }

        try {
            val location = createLocation.createLocation(name = name.trim())
            locationsById[location.id] = location
            model.value = success()
        } catch (e: Exception) {
            log.error("Error creating location", e)
            command.value = AnnounceSaveError(
                message = resources.getString(R.string.add_location_error_failed_to_save),
                action = resources.getString(R.string.add_location_saved_failed_action),
            )
            return@launch
        }

        command.value = AnnounceSaveSuccess(
            message = resources.getString(R.string.add_location_saved),
            action = resources.getString(R.string.add_location_saved_leave_edition),
        )
    }

    fun locationDeleteTapped(id: Long) = viewModelScope.launch {
        log.debug("locationDeleteTapped: $id")

        val removed = locationsById.remove(id)
        if (removed == null) {
            log.error("location not found")
            command.value = AnnounceDeleteError(
                message = resources.getString(R.string.add_location_delete_error),
            )
            return@launch
        }
        deletedId = id

        try {
            createLocation.deleteLocation(id)
            command.value = AnnounceDeleteSuccess(
                message = resources.getString(R.string.add_location_delete_success),
                action = resources.getString(R.string.add_location_delete_success_undo),
            )
        } catch (e: Exception) {
            log.error("Error deleting location", e)
            command.value = AnnounceDeleteError(
                message = resources.getString(R.string.add_location_delete_error),
            )
            return@launch
        }

        model.value = success()
    }

    fun deletedAction() = viewModelScope.launch {
        log.debug("deletedAction")
        val id = deletedId
        if (id == null) {
            log.error("id is null")
            command.value = AnnounceUndoResult(
                message = resources.getString(R.string.add_location_delete_success_undo_error),
            )
            return@launch
        }

        try {
            createLocation.undeleteLocation(id)
            val location = fetchLocations.locationById(id)
            locationsById[location.id] = location
        } catch (e: Exception) {
            log.error("Error creating location", e)
            command.value = AnnounceUndoResult(
                message = resources.getString(R.string.add_location_delete_success_undo_error),
            )
            return@launch
        }

        command.value = AnnounceUndoResult(
            message = resources.getString(R.string.add_location_delete_success_undo_success),
        )

        model.value = success()
    }

    fun back() {
        log.debug("back")
        command.value = NavigateBack
    }

    fun savedAction() {
        log.debug("savedAction")
        command.value = NavigateBack
    }

    fun errorAction() {
        log.debug("errorAction")
        command.value = NavigateToAddLocation(lastTriedLocationName)
    }

    fun locationEditionTapped(id: Long) {
        log.debug("locationEditionTapped: $id")
        editedId = id

        val location = locationsById[id]
        if (location == null) {
            log.error("location not found")
            command.value = AnnounceEditionError(
                message = resources.getString(R.string.add_location_edition_error),
            )
            return
        }

        command.value = NavigateToEditLocation(location.name)
    }

    fun locationEdited(name: String) = viewModelScope.launch {
        log.debug("locationEdited: $name")
        lastTriedLocationName = name

        val id = editedId
        if (id == null) {
            log.error("id is null")
            command.value = AnnounceEditionError(
                message = resources.getString(R.string.add_location_edition_error),
            )
            return@launch
        }

        if (name.isBlank()) {
            log.error("name is blank")
            command.value = AnnounceEditionError(
                message = resources.getString(R.string.add_location_error_invalid_name),
            )
            return@launch
        }

        try {
            createLocation.updateLocation(id, name)
            val location = fetchLocations.locationById(id)
            locationsById[location.id] = location
        } catch (e: Exception) {
            log.error("Error creating location", e)
            command.value = AnnounceEditionError(
                message = resources.getString(R.string.add_location_edition_error),
            )
            return@launch
        }

        command.value = AnnounceEditionSuccess(
            message = resources.getString(R.string.add_location_edition_success),
        )

        model.value = success()
    }

    private fun success() = Success(
        locationsById.values.sortedBy { it.name }.map { location ->
            LocationModel(
                id = location.id,
                name = location.name,
            )
        },
    )

}
