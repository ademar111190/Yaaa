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

    fun lastUsedLocation() = prefs.getString(LAST_USED_LOCATION, null)

    fun saveLastUsedLocation(location: String) = prefs.edit()
        .putString(LAST_USED_LOCATION, location)
        .apply()

    companion object {

        private const val SHARED_PREFERENCES_NAME = "YAAA_SHARED_PREFERENCES"
        private const val LAST_USED_LOCATION = "LAST_USED_LOCATION"

    }

}
