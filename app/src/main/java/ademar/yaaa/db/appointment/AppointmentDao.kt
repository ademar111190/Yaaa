package ademar.yaaa.db.appointment

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE

@Dao
interface AppointmentDao {

    @Insert(onConflict = REPLACE)
    suspend fun createOrUpdate(appointment: AppointmentEntity): Long?

    @Query("SELECT pk FROM appointments WHERE rowid = :rowId")
    suspend fun readPk(rowId: Long): Long?

    @Query("SELECT * FROM appointments ORDER BY appointments.date DESC")
    suspend fun readAll(): List<AppointmentEntity>

    @Query("SELECT * FROM appointments WHERE appointments.pk = :id")
    suspend fun read(id: Long): AppointmentEntity?

    @Query("SELECT * FROM appointments WHERE appointments.location_fk IN (:locationIds)")
    suspend fun read(locationIds: List<Long>): List<AppointmentEntity>

    @Query("DELETE FROM appointments WHERE appointments.pk = :id")
    suspend fun delete(id: Long)

}
