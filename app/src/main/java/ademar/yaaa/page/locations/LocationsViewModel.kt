package ademar.yaaa.page.locations

import ademar.yaaa.R
import ademar.yaaa.data.Location
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
) : AndroidViewModel(application) {

    private val log = LoggerFactory.getLogger("LocationsViewModel")
    val model = MutableLiveData<LocationsModel>(Initial)
    val command = MutableLiveData<LocationsCommand>()

    private var locationsById = mutableMapOf<Long, Location>()

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

        model.value = Success(locationsById.values.map { location ->
            LocationModel(
                id = location.id,
                name = location.name,
            )
        })
    }

    fun add() {
        log.debug("add")
    }

    fun locationDeleteTapped(id: Long) {
        log.debug("locationDeleteTapped: $id")
    }

    fun back() {
        log.debug("back")
        command.value = NavigateBack
    }

}
