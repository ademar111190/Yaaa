package ademar.yaaa.db

import ademar.yaaa.db.location.LocationDao
import ademar.yaaa.db.location.LocationEntity
import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class LocationTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: LocationDao

    @Before
    fun setUp() {
        val context = getApplicationContext<Context>()
        db = FakeAppDatabase.create(context)
        dao = db.locationDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun initialDb_readAll_shouldContainDefaultLocations() = runTest {
        val locations = dao.readAll()

        assertEquals(locations.size, 6)
        assertContentEquals(locations, initialLocations().toList())
    }

    @Test
    fun create_readAll_shouldContainCreatedLocation() = runTest {
        val location = LocationEntity(7, "New York", false)
        dao.create(location)

        val locations = dao.readAll()

        assertEquals(locations.size, 7)
        assertContentEquals(
            locations, listOf(
                *initialLocations(),
                LocationEntity(7, "New York", false),
            )
        )
    }

    @Test
    fun create_read_shouldContainCreatedLocation() = runTest {
        val location = LocationEntity(7, "New York", false)
        dao.create(location)

        val readLocation = dao.read(7)

        assertEquals(readLocation, location)
    }

    @Test
    fun delete_readAll_shouldNotContainDeletedLocation() = runTest {
        dao.delete(1)

        val locations = dao.readAll()

        assertEquals(locations.size, 5)
        assertContentEquals(
            locations, listOf(
                *initialLocations().drop(1).toTypedArray(),
            )
        )
    }

    @Test
    fun delete_read_shouldContainSoftDeletedLocation() = runTest {
        dao.delete(1)

        val location = dao.read(1)

        assertEquals(location, LocationEntity(1, "San Diego", true))
    }

    private fun initialLocations() = arrayOf(
        LocationEntity(1, "San Diego", false),
        LocationEntity(2, "St. George", false),
        LocationEntity(3, "Park City", false),
        LocationEntity(4, "Dallas", false),
        LocationEntity(5, "Memphis", false),
        LocationEntity(6, "Orlando", false),
    )
}
