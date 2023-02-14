package ademar.yaaa.usecase

import ademar.yaaa.Dummies.makeAppointment
import ademar.yaaa.Dummies.makeAppointmentEntity
import ademar.yaaa.Dummies.makeLocation
import ademar.yaaa.db.AppDatabase
import ademar.yaaa.db.appointment.AppointmentDao
import ademar.yaaa.usecase.mapper.AppointmentMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import kotlin.test.assertContentEquals

@OptIn(ExperimentalCoroutinesApi::class)
class FetchAppointmentsTest {

    private lateinit var usecase: FetchAppointments
    @Mock private lateinit var appDatabase: AppDatabase
    @Mock private lateinit var appointmentDao: AppointmentDao
    @Mock private lateinit var fetchLocations: FetchLocations
    @Mock private lateinit var appointmentMapper: AppointmentMapper

    @Before
    fun setUp() {
        openMocks(this)
        whenever(appDatabase.appointmentDao()).thenReturn(appointmentDao)
        whenever(appointmentMapper.mapToAppointment(any(), any())).thenReturn(makeAppointment())
        usecase = FetchAppointments(appDatabase, fetchLocations, appointmentMapper)
    }

    @Test
    fun allAppointments_whenAppointmentsAreEmpty_shouldReturnEmptyList() = runTest {
        whenever(appointmentDao.readAll()).thenReturn(emptyList())

        val appointments = usecase.allAppointments()

        assertContentEquals(appointments, emptyList())
    }

    @Test
    fun allAppointments_whenAppointmentsAreNotEmpty_shouldReturnAppointments() = runTest {
        whenever(fetchLocations.locationById(any())).thenReturn(makeLocation())
        whenever(appointmentDao.readAll()).thenReturn(listOf(makeAppointmentEntity()))

        val appointments = usecase.allAppointments()

        assertContentEquals(appointments, listOf(makeAppointment()))
    }

}
