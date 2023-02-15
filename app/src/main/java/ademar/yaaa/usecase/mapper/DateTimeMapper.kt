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

}
