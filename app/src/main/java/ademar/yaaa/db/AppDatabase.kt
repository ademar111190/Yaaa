package ademar.yaaa.db

import ademar.yaaa.db.appointment.AppointmentDao
import ademar.yaaa.db.appointment.AppointmentEntity
import ademar.yaaa.db.location.LocationEntity
import ademar.yaaa.db.location.LocationDao
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        AppointmentEntity::class,
        LocationEntity::class,
    ],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appointmentDao(): AppointmentDao

    abstract fun locationDao(): LocationDao

}
