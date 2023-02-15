package ademar.yaaa.usecase.mapper

import ademar.yaaa.R
import android.content.res.Resources
import dagger.Reusable
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.getDefault
import javax.inject.Inject

@Reusable
class DateTimeMapper @Inject constructor(
    private val resources: Resources,
) {

    fun mapToHourString(
        date: Date,
        locale: Locale = getDefault(),
    ): String = SimpleDateFormat(
        resources.getString(R.string.app_hour_format),
        locale,
    ).format(date)

    fun mapToDateString(
        date: Date,
        locale: Locale = getDefault(),
    ): String = SimpleDateFormat(
        resources.getString(R.string.app_date_format),
        locale,
    ).format(date)

    fun mapToHour(date: Date): Int {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal[Calendar.HOUR_OF_DAY]
    }

    fun mapToMinute(date: Date): Int {
        val cal = Calendar.getInstance()
        cal.time = date
        return cal[Calendar.MINUTE]
    }

    fun mergeToDate(
        date: Date,
        hour: Int,
        minute: Int,
    ): Date {
        val cal = Calendar.getInstance()
        cal.time = date
        cal[Calendar.HOUR_OF_DAY] = hour
        cal[Calendar.MINUTE] = minute
        return cal.time
    }

    fun mergeToDate(
        date: Date,
        newUtcDate: Long,
    ): Date {
        val newCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        newCal.timeInMillis = newUtcDate
        val cal = Calendar.getInstance()
        cal.time = date
        cal[Calendar.YEAR] = newCal[Calendar.YEAR]
        cal[Calendar.MONTH] = newCal[Calendar.MONTH]
        cal[Calendar.DAY_OF_MONTH] = newCal[Calendar.DAY_OF_MONTH]
        return cal.time
    }

}
