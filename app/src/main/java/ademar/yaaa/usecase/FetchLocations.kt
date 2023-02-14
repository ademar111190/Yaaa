package ademar.yaaa.usecase

import ademar.yaaa.R
import ademar.yaaa.data.Location
import ademar.yaaa.db.AppDatabase
import ademar.yaaa.usecase.mapper.LocationMapper
import android.content.res.Resources
import dagger.Reusable
import org.slf4j.LoggerFactory
import javax.inject.Inject

@Reusable
class FetchLocations @Inject constructor(
    private val appDatabase: AppDatabase,
    private val locationMapper: LocationMapper,
    private val resources: Resources,
) {

    private val log = LoggerFactory.getLogger("FetchLocations")

    suspend fun allLocations() = appDatabase.locationDao().readAll()
        .map { locationMapper.mapToLocation(it) }

    suspend fun locationById(id: Long): Location {
        val entity = appDatabase.locationDao().read(id)
        if (entity != null) {
            return locationMapper.mapToLocation(entity)
        }
        // The location should always be present, but if it's not, we return a default one
        // to do not break the user experience, and log the error to be on track and fix it
        log.error("Location with id $id not found")
        return Location(id, resources.getString(R.string.unknown_location))
    }

}
