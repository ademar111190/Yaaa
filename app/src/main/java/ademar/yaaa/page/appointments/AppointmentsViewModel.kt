package ademar.yaaa.page.appointments

import ademar.yaaa.R
import ademar.yaaa.usecase.FetchAppointments
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
) : AndroidViewModel(application) {

    private val log = LoggerFactory.getLogger("AppointmentsViewModel")
    val model = MutableLiveData<AppointmentsModel>(Initial)

    fun load() = viewModelScope.launch {
        log.debug("load")
        try {
            model.value = Loading
            val appointments = fetchAppointments.allAppointments()
            model.value = Success(appointments)
        } catch (e: Exception) {
            log.error("Error fetching appointments", e)
            model.value = Error(resources.getString(R.string.appointments_error_fetching_appointments))
        }
    }

}
