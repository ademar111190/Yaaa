package ademar.yaaa.usecase.mapper

import android.content.res.Resources
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.time.Instant
import java.util.*
import kotlin.test.assertEquals

class DateTimeMapperTest {

    private lateinit var mapper: DateTimeMapper
    @Mock private lateinit var resources: Resources

    @Before
    fun setUp() {
        openMocks(this)
        mapper = DateTimeMapper(resources)
    }

    @Test
    fun mapToHourString_englishLocale_returnsCorrectString() {
        whenever(resources.getString(any())).thenReturn("h:mm a")
        val result = mapper.mapToHourString(date)
        assertEquals(hourEnglish, result)
    }

    @Test
    fun mapToDateString_englishLocale_returnsCorrectString() {
        whenever(resources.getString(any())).thenReturn("EEE, MMM d, yyyy")
        val result = mapper.mapToDateString(date)
        assertEquals(dateEnglish, result)
    }

    @Test
    fun mapToHourString_portugueseLocale_returnsCorrectString() {
        whenever(resources.getString(any())).thenReturn("HH:mm")
        val result = mapper.mapToHourString(date, Locale("pt", "BR"))
        assertEquals(hourPortuguese, result)
    }

    @Test
    fun mapToDateString_portugueseLocale_returnsCorrectString() {
        whenever(resources.getString(any())).thenReturn("EEE, d 'de' MMM 'de' yyyy")
        val result = mapper.mapToDateString(date, Locale("pt", "BR"))
        assertEquals(datePortuguese, result)
    }

    private val date = Date.from(Instant.ofEpochMilli(1676486460000))
    private val hourEnglish = "3:41 PM"
    private val dateEnglish = "Wed, Feb 15, 2023"
    private val hourPortuguese = "15:41"
    private val datePortuguese = "qua, 15 de fev de 2023"

}
