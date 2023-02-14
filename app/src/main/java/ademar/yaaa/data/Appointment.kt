package ademar.yaaa.data

import java.util.Date

data class Appointment(
    val id: Long,
    val date: Date,
    val location: Location,
    val description: String,
)
