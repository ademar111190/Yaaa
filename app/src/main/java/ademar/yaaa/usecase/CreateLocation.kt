package ademar.yaaa.usecase

import ademar.yaaa.data.Location
import ademar.yaaa.db.AppDatabase
import ademar.yaaa.db.location.LocationEntity
import ademar.yaaa.usecase.mapper.LocationMapper
import dagger.Reusable
import javax.inject.Inject

@Reusable
class CreateLocation @Inject constructor(
    private val appDatabase: AppDatabase,
    private val locationMapper: LocationMapper,
) {

    suspend fun createLocation(
        id: Long? = null,
        name: String,
    ): Location {
        val rowId = appDatabase.locationDao().createOrUpdate(
            LocationEntity(
                id = id ?: 0L,
                name = name,
                deleted = false,
            )
        ) ?: throw IllegalStateException("Failed to save location.")
        val pk = appDatabase.locationDao().readPk(rowId)
            ?: throw IllegalStateException("Failed to save location.")
        val entity = appDatabase.locationDao()
            .read(pk) ?: throw IllegalStateException("Failed to save location.")
        return locationMapper.mapToLocation(entity)
    }

    suspend fun deleteLocation(id: Long) {
        appDatabase.locationDao().delete(id)
    }

    suspend fun undeleteLocation(id: Long) {
        appDatabase.locationDao().undelete(id)
    }

    suspend fun updateLocation(id: Long, name: String) {
        appDatabase.locationDao().createOrUpdate(
            LocationEntity(
                id = id,
                name = name,
                deleted = false,
            )
        )
    }

}
