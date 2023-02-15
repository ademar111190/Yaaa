package ademar.yaaa.page.appointment

import ademar.yaaa.R
import ademar.yaaa.data.Location
import ademar.yaaa.usecase.DateCreator
import ademar.yaaa.usecase.FetchLocations
import ademar.yaaa.usecase.mapper.DateTimeMapper
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
class AppointmentViewModel @Inject constructor(
    application: Application,
    private val resources: Resources,
    dateCreator: DateCreator,
    private val dateTimeMapper: DateTimeMapper,
    private val fetchLocations: FetchLocations,
) : AndroidViewModel(application) {

    private val log = LoggerFactory.getLogger("AppointmentViewModel")
    val model = MutableLiveData<AppointmentModel>(Initial)
    val command = MutableLiveData<AppointmentCommand>()

    private var dateTime = dateCreator.create()
    private var location: Location? = null
    private var description = ""
    private val locationsMap = mutableMapOf<String, Location>()

    fun load() = viewModelScope.launch {
        log.debug("load")
        model.value = Loading

        try {
            val locations = fetchLocations.allLocations()
            locations.forEach { location ->
                locationsMap[location.name] = location
            }
        } catch (e: Exception) {
            log.error("Error fetching locations", e)
            model.value = Error(resources.getString(R.string.appointment_error_fetching_locations))
            return@launch
        }

        model.value = success()
    }

    fun updateDescription(newDescription: String) {
        log.debug("updateDescription: $newDescription")
        description = newDescription
    }

    fun updateLocation(newLocation: String?) {
        log.debug("updateLocation: $newLocation")
        location = if (newLocation != null && locationsMap.containsKey(newLocation)) {
            locationsMap[newLocation]
        } else {
            null
        }
    }

    fun changeDate() {
        log.debug("changeDate")
        command.value = NavigateToDatePicker(dateTime)
    }

    fun dateChanged(date: Long) {
        log.debug("dateChanged: $date")
        dateTime = dateTimeMapper.mergeToDate(dateTime, date)
        model.value = success()
    }

    fun changeHour() {
        log.debug("changeHour")
        command.value = NavigateToTimePicker(
            hour = dateTimeMapper.mapToHour(dateTime),
            minute = dateTimeMapper.mapToMinute(dateTime),
        )
    }

    fun timeChanged(hour: Int, minute: Int) {
        log.debug("timeChanged: $hour:$minute")
        dateTime = dateTimeMapper.mergeToDate(dateTime, hour, minute)
        model.value = success()
    }

    fun back() {
        log.debug("back")
        command.value = NavigateBack
    }

    private fun success() = Success(
        hour = dateTimeMapper.mapToHourString(dateTime),
        date = dateTimeMapper.mapToDateString(dateTime),
        location = location?.name,
        description = description,
        locationOptions = locationsMap.keys.toSet(),
    )

}
