package ademar.yaaa.usecase

import ademar.yaaa.Dummies.initialLocationEntities
import ademar.yaaa.Dummies.initialLocations
import ademar.yaaa.data.Location
import ademar.yaaa.db.AppDatabase
import ademar.yaaa.db.location.LocationDao
import ademar.yaaa.usecase.mapper.LocationMapper
import android.content.res.Resources
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class FetchLocationsTest {

    private lateinit var usecase: FetchLocations
    @Mock private lateinit var appDatabase: AppDatabase
    @Mock private lateinit var locationDao: LocationDao
    @Mock private lateinit var locationMapper: LocationMapper
    @Mock private lateinit var resources: Resources

    @Before
    fun setUp() {
        openMocks(this)
        whenever(appDatabase.locationDao()).thenReturn(locationDao)
        initialLocations().zip(initialLocationEntities()).forEach { (location, entity) ->
            whenever(locationMapper.mapToLocationEntity(location)).thenReturn(entity)
            whenever(locationMapper.mapToLocation(entity)).thenReturn(location)
        }
        whenever(resources.getString(any())).thenReturn("Unknown Location")
        usecase = FetchLocations(appDatabase, locationMapper, resources)
    }

    @Test
    fun allLocations_whenLocationsAreEmpty_shouldReturnEmptyList() = runTest {
        whenever(locationDao.readAll()).thenReturn(emptyList())

        val locations = usecase.allLocations()

        assertContentEquals(locations, emptyList())
    }

    @Test
    fun allLocations_whenLocationsAreNotEmpty_shouldReturnLocations() = runTest {
        whenever(locationDao.readAll()).thenReturn(initialLocationEntities().toList())

        val locations = usecase.allLocations()

        assertContentEquals(locations, initialLocations())
    }

    @Test
    fun locationById_whenLocationExists_shouldReturnLocation() = runTest {
        whenever(locationDao.read(1)).thenReturn(initialLocationEntities().first())

        val location = usecase.locationById(1)

        assertEquals(location, initialLocations().first())
    }

    @Test
    fun locationById_whenLocationDoesNotExist_shouldReturnUnknownLocation() = runTest {
        whenever(locationDao.read(1)).thenReturn(null)

        val location = usecase.locationById(1)

        assertEquals(location, Location(1, "Unknown Location"))
    }

}
