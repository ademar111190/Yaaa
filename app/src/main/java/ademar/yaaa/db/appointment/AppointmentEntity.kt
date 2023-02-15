package ademar.yaaa.db.appointment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "appointments")
data class AppointmentEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "pk") val id: Long = 0,
    @ColumnInfo(name = "location_fk") val locationId: Long,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "description") val description: String,
)
