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

    fun mapToString(
        date: Date,
    ): String = format().format(date)

    fun mapToDate(
        date: String,
    ): Date = format().parse(date)!!

    private fun format() = SimpleDateFormat(
        resources.getString(R.string.app_date_format),
        getDefault(),
    )

}
