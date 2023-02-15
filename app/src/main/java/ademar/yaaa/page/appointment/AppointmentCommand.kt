package ademar.yaaa.page.appointment

import java.util.*

sealed class AppointmentCommand

object NavigateBack : AppointmentCommand()

data class NavigateToDatePicker(
    val date: Date,
) : AppointmentCommand()

data class NavigateToTimePicker(
    val hour: Int,
    val minute: Int,
) : AppointmentCommand()

data class AnnounceSaveSuccess(
    val message: String,
    val action: String,
) : AppointmentCommand()
