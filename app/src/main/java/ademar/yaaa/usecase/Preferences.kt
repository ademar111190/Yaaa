package ademar.yaaa.usecase

import android.content.Context
import android.content.Context.MODE_PRIVATE
import dagger.Reusable
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@Reusable
class Preferences @Inject constructor(
    @ApplicationContext context: Context,
) {

    private val prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)

    fun lastUsedLocationId() = prefs.getLong(LAST_USED_LOCATION, NO_LOCATION_ID)

    fun saveLastUsedLocationId(locationId: Long) = prefs.edit()
        .putLong(LAST_USED_LOCATION, locationId)
        .apply()

    companion object {

        const val NO_LOCATION_ID = -1L
        private const val SHARED_PREFERENCES_NAME = "YAAA_SHARED_PREFERENCES"
        private const val LAST_USED_LOCATION = "LAST_USED_LOCATION"

    }

}
