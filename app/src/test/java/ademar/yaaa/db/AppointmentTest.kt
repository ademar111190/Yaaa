package ademar.yaaa.db

import ademar.yaaa.Dummies.makeAppointmentEntity
import ademar.yaaa.db.appointment.AppointmentDao
import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class AppointmentTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: AppointmentDao

    @Before
    fun setUp() {
        val context = getApplicationContext<Context>()
        db = FakeAppDatabase.create(context)
        dao = db.appointmentDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun initialDb_readAll_shouldReturnEmptyList() = runTest {
        val appointments = dao.readAll()
        assertEquals(appointments.size, 0)
    }

    @Test
    fun create_readAll_shouldContainCreatedAppointment() = runTest {
        val appointment = makeAppointmentEntity()
        dao.create(appointment)

        val appointments = dao.readAll()

        assertEquals(appointments.size, 1)
        assertContentEquals(appointments, listOf(appointment))
    }

    @Test
    fun dbContainsAppointmentsInDifferentLocations_readGivenAnLocation_shouldReturnOnlyAppointmentsFromThatLocation() =
        runTest {
            val appointment1 = makeAppointmentEntity()
            val appointment2 = makeAppointmentEntity(id = 2, locationId = 2)
            val appointment3 = makeAppointmentEntity(id = 3)
            dao.create(appointment1)
            dao.create(appointment2)
            dao.create(appointment3)

            val appointments = dao.read(listOf(1))

            assertEquals(appointments.size, 2)
            assertContentEquals(appointments, listOf(appointment1, appointment3))
        }

    @Test
    fun update_readAll_shouldContainUpdatedAppointment() = runTest {
        val appointment = makeAppointmentEntity()
        dao.create(appointment)
        val updatedAppointment = appointment.copy(description = "An Updated Description")
        dao.update(updatedAppointment)

        val appointments = dao.readAll()

        assertEquals(appointments.size, 1)
        assertContentEquals(appointments, listOf(updatedAppointment))
    }

    @Test
    fun delete_readAll_shouldNotContainDeletedAppointment() = runTest {
        val appointment = makeAppointmentEntity()
        dao.create(appointment)
        dao.delete(appointment)

        val appointments = dao.readAll()

        assertEquals(appointments.size, 0)
    }

}
