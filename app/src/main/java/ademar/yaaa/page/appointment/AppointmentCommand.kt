package ademar.yaaa.page.appointment

import java.util.Date

sealed class AppointmentCommand

object NavigateBack : AppointmentCommand()

data class NavigateToDatePicker(
    val date: Date,
) : AppointmentCommand()

data class NavigateToTimePicker(
    val hour: Int,
    val minute: Int,
) : AppointmentCommand()
