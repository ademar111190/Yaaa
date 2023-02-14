package ademar.yaaa.usecase.mapper

import ademar.yaaa.Dummies.makeLocation
import ademar.yaaa.Dummies.makeLocationEntity
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

}
