package ademar.yaaa.usecase.mapper

import ademar.yaaa.data.Location
import ademar.yaaa.db.location.LocationEntity
import dagger.Reusable
import javax.inject.Inject

@Reusable
class LocationMapper @Inject constructor() {

    fun mapToLocationEntity(
        location: Location,
    ) = LocationEntity(
        id = location.id,
        name = location.name,
        deleted = false,
    )

    fun mapToLocation(
        locationEntity: LocationEntity,
    ) = Location(
        id = locationEntity.id,
        name = locationEntity.name,
    )

}
