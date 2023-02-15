package ademar.yaaa.usecase

import android.os.Build
import dagger.Reusable
import java.time.Instant
import java.util.*
import javax.inject.Inject

@Reusable
class DateCreator @Inject constructor() {

    fun create(): Date {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Date.from(Instant.now())
        } else {
            Date()
        }
    }

}
