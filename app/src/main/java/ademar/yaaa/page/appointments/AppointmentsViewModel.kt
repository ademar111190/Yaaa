package ademar.yaaa.page.appointments

import ademar.yaaa.R
import ademar.yaaa.data.Appointment
import ademar.yaaa.usecase.FetchAppointments
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
class AppointmentsViewModel @Inject constructor(
    application: Application,
    private val resources: Resources,
    private val fetchAppointments: FetchAppointments,
    private val dateTimeMapper: DateTimeMapper,
) : AndroidViewModel(application) {

    private val log = LoggerFactory.getLogger("AppointmentsViewModel")
    val model = MutableLiveData<AppointmentsModel>(Initial)
    val command = MutableLiveData<AppointmentsCommand>()

    fun load() = viewModelScope.launch {
        log.debug("load")
        try {
            model.value = Loading
            val appointments = fetchAppointments.allAppointments()
            model.value = Success(appointments.toItems())
        } catch (e: Exception) {
            log.error("Error fetching appointments", e)
            model.value = Error(resources.getString(R.string.appointments_error_fetching_appointments))
        }
    }

    fun checkChanges() = viewModelScope.launch {
        log.debug("checkChanges")
        try {
            val appointments = fetchAppointments.allAppointments()
            model.value = Success(appointments.toItems())
        } catch (e: Exception) {
            log.error("Error fetching appointments", e)
        }
    }

    fun add() {
        log.debug("add")
        command.value = NavigateToAppointmentCreation
    }

    private fun Iterable<Appointment>.toItems() = map { appointment ->
        AppointmentItem(
            id = appointment.id,
            hour = dateTimeMapper.mapToHourString(appointment.date),
            date = dateTimeMapper.mapToDateString(appointment.date),
            location = appointment.location.name,
            description = appointment.description,
        )
    }

    fun appointmentTapped(id: Long) {
        log.debug("appointmentTapped: $id")
        command.value = NavigateToAppointmentDetails(id)
    }

    fun editLocations() {
        log.debug("editLocations")
        command.value = NavigateToLocations
    }

}
