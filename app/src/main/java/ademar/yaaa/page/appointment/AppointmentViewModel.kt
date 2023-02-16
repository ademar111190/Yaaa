package ademar.yaaa.page.appointment

import ademar.yaaa.R
import ademar.yaaa.data.Location
import ademar.yaaa.usecase.*
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
    private val fetchAppointments: FetchAppointments,
    private val fetchLocations: FetchLocations,
    private val createAppointment: CreateAppointment,
    private val preferences: Preferences,
) : AndroidViewModel(application) {

    private val log = LoggerFactory.getLogger("AppointmentViewModel")
    val model = MutableLiveData<AppointmentModel>(Initial)
    val command = MutableLiveData<AppointmentCommand>()

    private var appointmentId: Long? = null
    private var dateTime = dateCreator.create()
    private var location: Location? = null
    private var description = ""
    private val locationsByName = mutableMapOf<String, Location>()

    fun load(
        appointmentId: Long? = null,
    ) = viewModelScope.launch {
        log.debug("load appointmentId: $appointmentId")
        model.value = Loading

        try {
            val locations = fetchLocations.allLocations()
            val locationsById = mutableMapOf<Long, Location>()
            locations.forEach { location ->
                locationsByName[location.name] = location
                locationsById[location.id] = location
            }
            location = locationsById[preferences.lastUsedLocationId()]
        } catch (e: Exception) {
            log.error("Error fetching locations", e)
            model.value = Error(resources.getString(R.string.appointment_error_fetching_locations))
            return@launch
        }

        if (appointmentId != null) {
            try {
                val appointment = fetchAppointments.appointmentById(appointmentId)
                if (appointment != null) {
                    this@AppointmentViewModel.appointmentId = appointment.id
                    dateTime = appointment.date
                    location = appointment.location
                    description = appointment.description

                    model.value = success(
                        saveStatus = SaveStatus.SAVED,
                    )
                    return@launch
                }
            } catch (e: Exception) {
                log.error("Error fetching appointment", e)
                model.value = Error(resources.getString(R.string.appointment_error_fetching_appointment))
                return@launch
            }
        }

        model.value = success(
            saveStatus = SaveStatus.NOT_SAVED,
        )
    }

    fun updateDescription(newDescription: String) {
        log.debug("updateDescription: $newDescription")
        if (newDescription == description) {
            return
        }

        description = newDescription
        model.value = success(
            saveStatus = SaveStatus.NOT_SAVED,
        )
    }

    fun updateLocation(newLocation: String?) {
        log.debug("updateLocation: $newLocation")
        if (newLocation == location?.name) {
            return
        }

        location = if (newLocation != null && locationsByName.containsKey(newLocation)) {
            locationsByName[newLocation]
        } else {
            null
        }
        location?.id?.let { preferences.saveLastUsedLocationId(it) }

        model.value = success(
            saveStatus = SaveStatus.NOT_SAVED,
        )
    }

    fun changeDate() {
        log.debug("changeDate")
        command.value = NavigateToDatePicker(dateTime)
    }

    fun dateChanged(date: Long) {
        log.debug("dateChanged: $date")
        dateTime = dateTimeMapper.mergeToDate(dateTime, date)
        model.value = success(
            saveStatus = SaveStatus.NOT_SAVED,
        )
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
        model.value = success(
            saveStatus = SaveStatus.NOT_SAVED,
        )
    }

    fun save() = viewModelScope.launch {
        log.debug("save")
        model.value = success(
            saveStatus = SaveStatus.SAVING,
        )

        val validLocation = location
        if (validLocation == null) {
            log.error("Error creating appointment: location is null")
            model.value = Error(resources.getString(R.string.appointment_error_invalid_location))
            return@launch
        }

        try {
            val entity = createAppointment.createAppointment(
                id = appointmentId,
                date = dateTime,
                location = validLocation,
                description = description,
            )
            appointmentId = entity.id
        } catch (e: Exception) {
            log.error("Error creating appointment", e)
            model.value = Error(resources.getString(R.string.appointment_error_failed_to_save))
            return@launch
        }

        model.value = success(
            saveStatus = SaveStatus.SAVED,
        )

        command.value = AnnounceSaveSuccess(
            message = resources.getString(R.string.appointment_saved),
            action = resources.getString(R.string.appointment_saved_leave_edition),
        )
    }

    fun savedAction() {
        log.debug("savedAction")
        command.value = NavigateBack
    }

    fun delete() = viewModelScope.launch {
        log.debug("delete")

        val validAppointmentId = appointmentId
        if (validAppointmentId == null) {
            log.error("Error deleting appointment: appointmentId is null")
            model.value = Error(resources.getString(R.string.appointment_error_failed_to_delete))
            return@launch
        }

        try {
            createAppointment.deleteAppointment(validAppointmentId)
        } catch (e: Exception) {
            log.error("Error deleting appointment", e)
            model.value = Error(resources.getString(R.string.appointment_error_failed_to_delete))
            return@launch
        }
        appointmentId = null

        command.value = AnnounceDeleteSuccess(
            message = resources.getString(R.string.appointment_deleted),
            action = resources.getString(R.string.appointment_deleted_undo),
        )

        model.value = success(
            saveStatus = SaveStatus.NOT_SAVED,
        )
    }

    fun deletedAction() {
        log.debug("deleteAction")
        save()
    }

    fun back() {
        log.debug("back")
        command.value = NavigateBack
    }

    private fun success(
        saveStatus: SaveStatus,
    ) = Success(
        hour = dateTimeMapper.mapToHourString(dateTime),
        date = dateTimeMapper.mapToDateString(dateTime),
        location = location?.name,
        description = description,
        locationOptions = locationsByName.keys.toSet(),
        locationIndex = location?.name?.let { locationsByName.keys.indexOf(it) } ?: -1,
        saveStatus = saveStatus,
        saveLabel = appointmentId?.let {
            resources.getString(R.string.appointment_save_update)
        } ?: resources.getString(R.string.appointment_save_create),
        deleteEnabled = appointmentId != null,
    )

}
