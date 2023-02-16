package ademar.yaaa.db.location

import androidx.room.*

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "pk") val id: Long = 0L,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "deleted") val deleted: Boolean,
)
