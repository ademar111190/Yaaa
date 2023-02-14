package ademar.yaaa.db.appointment

import androidx.room.*

@Dao
interface AppointmentDao {

    @Insert
    suspend fun create(appointment: AppointmentEntity)

    @Query("SELECT * FROM appointments")
    suspend fun readAll(): List<AppointmentEntity>

    @Query("SELECT * FROM appointments WHERE appointments.pk = :id")
    suspend fun read(id: Long): AppointmentEntity

    @Query("SELECT * FROM appointments WHERE appointments.location_fk IN (:locationIds)")
    suspend fun read(locationIds: List<Long>): List<AppointmentEntity>

    @Update
    suspend fun update(appointment: AppointmentEntity)

    @Delete
    suspend fun delete(appointment: AppointmentEntity)

}
