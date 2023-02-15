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
    fun mapToString_englishLocale_returnsCorrectString() {
        whenever(resources.getString(any())).thenReturn(patternEnglish)
        val result = mapper.mapToString(date)
        assertEquals(stringEnglish, result)
    }

    @Test
    fun mapToString_portugueseLocale_returnsCorrectString() {
        whenever(resources.getString(any())).thenReturn(patternPortuguese)
        val result = mapper.mapToString(date)
        assertEquals(stringPortuguese, result)
    }

    @Test
    fun mapToDate_englishLocale_returnsCorrectDate() {
        whenever(resources.getString(any())).thenReturn(patternEnglish)
        val result = mapper.mapToDate(stringEnglish)
        assertEquals(date, result)
    }

    @Test
    fun mapToDate_portugueseLocale_returnsCorrectDate() {
        whenever(resources.getString(any())).thenReturn(patternPortuguese)
        val result = mapper.mapToDate(stringPortuguese)
        assertEquals(date, result)
    }

    private val date = Date.from(Instant.ofEpochMilli(1676486460000))
    private val stringEnglish = "3:41 PM, Feb 15, 2023"
    private val patternEnglish = "h:mm a',' MMM dd',' yyyy"
    private val stringPortuguese = "15:41 15/02/2023"
    private val patternPortuguese = "HH:mm dd/MM/yyyy"

}
