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

    fun locationDeleteTapped(id: Long) {
        log.debug("locationDeleteTapped: $id")
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

    private fun success() = Success(
        locationsById.values.sortedBy { it.name }.map { location ->
            LocationModel(
                id = location.id,
                name = location.name,
            )
        },
    )

}
