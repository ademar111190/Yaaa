package ademar.yaaa.db

import androidx.room.TypeConverter
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Converters @Inject constructor() {

    @TypeConverter
    fun dateFromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

}
