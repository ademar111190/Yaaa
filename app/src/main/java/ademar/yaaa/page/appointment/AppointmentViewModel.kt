package ademar.yaaa.page.appointment

import ademar.yaaa.usecase.DateCreator
import ademar.yaaa.usecase.mapper.DateTimeMapper
import android.app.Application
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
    dateCreator: DateCreator,
    private val dateTimeMapper: DateTimeMapper,
) : AndroidViewModel(application) {

    private val log = LoggerFactory.getLogger("AppointmentViewModel")
    val model = MutableLiveData<AppointmentModel>(Initial)
    val command = MutableLiveData<AppointmentCommand>()

    private var date = dateCreator.create()
    private var location = ""
    private var description = ""

    fun load() = viewModelScope.launch {
        log.debug("load")
        model.value = Success(
            date = dateTimeMapper.mapToString(date),
            location = location,
            description = description,
        )
    }

    fun updateDescription(description: String) = viewModelScope.launch {
        log.debug("updateDescription: $description")
        this@AppointmentViewModel.description = description
    }

    fun back() {
        log.debug("back")
        command.value = NavigateBack
    }

}
