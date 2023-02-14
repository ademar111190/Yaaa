package ademar.yaaa.db.location

import androidx.room.*

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey @ColumnInfo(name = "pk") val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "deleted") val deleted: Boolean,
)
