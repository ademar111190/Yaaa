package ademar.yaaa.db.location

import androidx.room.*

@Dao
interface LocationDao {

    @Insert
    suspend fun create(location: LocationEntity)

    @Query("SELECT * FROM locations WHERE deleted = 0 ORDER BY name ASC")
    suspend fun readAll(): List<LocationEntity>

    @Query("SELECT * FROM locations WHERE pk = :id")
    suspend fun read(id: Long): LocationEntity?

    // Instead of delete, we use a soft delete to keep the data consistency
    @Query("UPDATE locations SET deleted = 1 WHERE pk = :locationId")
    suspend fun delete(locationId: Long)

}
