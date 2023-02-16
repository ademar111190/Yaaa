package ademar.yaaa.usecase

import ademar.yaaa.Dummies.makeAppointment
import ademar.yaaa.Dummies.makeAppointmentEntity
import ademar.yaaa.db.AppDatabase
import ademar.yaaa.db.appointment.AppointmentDao
import ademar.yaaa.usecase.mapper.AppointmentMapper
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
class CreateAppointmentTest {

    private lateinit var usecase: CreateAppointment
    @Mock private lateinit var appDatabase: AppDatabase
    @Mock private lateinit var appointmentDao: AppointmentDao
    @Mock private lateinit var appointmentMapper: AppointmentMapper


    @Before
    fun setUp() {
        openMocks(this)
        whenever(appDatabase.appointmentDao()).thenReturn(appointmentDao)
        whenever(appointmentMapper.mapToAppointment(any(), any())).thenReturn(makeAppointment())
        usecase = CreateAppointment(appDatabase, appointmentMapper)
    }

    @Test
    fun createAppointment_whenAppointmentIsCreated_shouldReturnCreatedAppointment() = runTest {
        val appointment = makeAppointment()
        whenever(appointmentDao.createOrUpdate(any())).thenReturn(1)
        whenever(appointmentDao.readPk(1)).thenReturn(2)
        whenever(appointmentDao.read(2)).thenReturn(makeAppointmentEntity())

        val createdAppointment = usecase.createAppointment(
            date = appointment.date,
            location = appointment.location,
            description = appointment.description,
        )

        assertEquals(createdAppointment, appointment)
    }

    @Test(expected = IllegalStateException::class)
    fun createAppointment_whenFailedToReadPk_shouldThrowException() = runTest {
        val appointment = makeAppointment()
        whenever(appointmentDao.createOrUpdate(any())).thenReturn(1)
        whenever(appointmentDao.readPk(1)).thenReturn(2)
        whenever(appointmentDao.read(2)).thenReturn(null)

        usecase.createAppointment(
            date = appointment.date,
            location = appointment.location,
            description = appointment.description,
        )
    }

    @Test(expected = IllegalStateException::class)
    fun createAppointment_whenFailedToReadRowId_shouldThrowException() = runTest {
        val appointment = makeAppointment()
        whenever(appointmentDao.createOrUpdate(any())).thenReturn(1)
        whenever(appointmentDao.readPk(1)).thenReturn(null)

        usecase.createAppointment(
            date = appointment.date,
            location = appointment.location,
            description = appointment.description,
        )
    }

    @Test(expected = IllegalStateException::class)
    fun createAppointment_whenFailedToCreate_shouldThrowException() = runTest {
        val appointment = makeAppointment()
        whenever(appointmentDao.createOrUpdate(any())).thenReturn(null)

        usecase.createAppointment(
            date = appointment.date,
            location = appointment.location,
            description = appointment.description,
        )
    }

    @Test
    fun deleteAppointment_whenAppointmentIsDeleted_shouldNotThrowException() = runTest {
        val appointment = makeAppointment()
        whenever(appointmentMapper.mapToAppointmentEntity(appointment)).thenReturn(makeAppointmentEntity())

        usecase.deleteAppointment(appointment)
    }

}
