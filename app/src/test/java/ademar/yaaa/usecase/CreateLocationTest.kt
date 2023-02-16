package ademar.yaaa.usecase

import ademar.yaaa.Dummies.makeLocation
import ademar.yaaa.Dummies.makeLocationEntity
import ademar.yaaa.db.AppDatabase
import ademar.yaaa.db.location.LocationDao
import ademar.yaaa.usecase.mapper.LocationMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CreateLocationTest {

    private lateinit var usecase: CreateLocation
    @Mock private lateinit var appDatabase: AppDatabase
    @Mock private lateinit var locationDao: LocationDao
    @Mock private lateinit var locationMapper: LocationMapper

    @Before
    fun setUp() {
        openMocks(this)
        whenever(appDatabase.locationDao()).thenReturn(locationDao)
        whenever(locationMapper.mapToLocation(any())).thenReturn(makeLocation())
        usecase = CreateLocation(appDatabase, locationMapper)
    }

    @Test
    fun createLocation_whenLocationIsCreated_shouldReturnCreatedLocation() = runTest {
        val location = makeLocation()
        whenever(locationDao.createOrUpdate(any())).thenReturn(1)
        whenever(locationDao.readPk(1)).thenReturn(2)
        whenever(locationDao.read(2)).thenReturn(makeLocationEntity())

        val createdLocation = usecase.createLocation(
            name = location.name,
        )

        assertEquals(createdLocation, location)
    }

    @Test(expected = IllegalStateException::class)
    fun createLocation_whenFailedToReadPk_shouldThrowException() = runTest {
        val location = makeLocation()
        whenever(locationDao.createOrUpdate(any())).thenReturn(1)
        whenever(locationDao.readPk(1)).thenReturn(2)
        whenever(locationDao.read(2)).thenReturn(null)

        usecase.createLocation(
            name = location.name,
        )
    }

    @Test(expected = IllegalStateException::class)
    fun createLocation_whenFailedToReadRowId_shouldThrowException() = runTest {
        val location = makeLocation()
        whenever(locationDao.createOrUpdate(any())).thenReturn(1)
        whenever(locationDao.readPk(1)).thenReturn(null)

        usecase.createLocation(
            name = location.name,
        )
    }

    @Test(expected = IllegalStateException::class)
    fun createLocation_whenFailedToCreate_shouldThrowException() = runTest {
        val location = makeLocation()
        whenever(locationDao.createOrUpdate(any())).thenReturn(null)

        usecase.createLocation(
            name = location.name,
        )
    }

    @Test
    fun deleteLocation_whenLocationIsDeleted_shouldNotThrowException() = runTest {
        val location = makeLocation()
        whenever(locationMapper.mapToLocationEntity(location)).thenReturn(makeLocationEntity())

        usecase.deleteLocation(location.id)
    }

    @Test
    fun undeleteLocation() = runTest {
        val location = makeLocation()
        whenever(locationMapper.mapToLocationEntity(location)).thenReturn(makeLocationEntity())
        usecase.undeleteLocation(location.id)
    }

}
