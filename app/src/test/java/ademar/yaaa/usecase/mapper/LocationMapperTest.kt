package ademar.yaaa.usecase.mapper

import ademar.yaaa.data.Location
import ademar.yaaa.db.location.LocationEntity
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class LocationMapperTest {

    private lateinit var mapper: LocationMapper

    @Before
    fun setUp() {
        mapper = LocationMapper()
    }

    @Test
    fun mapToLocationEntity() {
        val location = makeLocation()

        val locationEntity = mapper.mapToLocationEntity(location)

        assertEquals(locationEntity, makeLocationEntity())
    }

    @Test
    fun mapToLocation() {
        val locationEntity = makeLocationEntity()

        val location = mapper.mapToLocation(locationEntity)

        assertEquals(location, makeLocation())
    }

    private fun makeLocation() = Location(
        id = 1,
        name = "name",
    )

    private fun makeLocationEntity() = LocationEntity(
        id = 1,
        name = "name",
        deleted = false,
    )

}
