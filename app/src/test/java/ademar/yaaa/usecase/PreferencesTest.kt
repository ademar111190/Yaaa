package ademar.yaaa.usecase

import ademar.yaaa.usecase.Preferences.Companion.NO_LOCATION_ID
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class PreferencesTest {

    private lateinit var usecase: Preferences
    @Mock private lateinit var context: Context
    @Mock private lateinit var preferences: SharedPreferences
    @Mock private lateinit var editor: SharedPreferences.Editor

    @Before
    fun setUp() {
        openMocks(this)
        whenever(context.getSharedPreferences("YAAA_SHARED_PREFERENCES", MODE_PRIVATE)).thenReturn(preferences)
        whenever(preferences.edit()).thenReturn(editor)
        whenever(editor.putLong(any(), any())).thenReturn(editor)
        usecase = Preferences(context)
    }

    @Test
    fun testLastUsedLocation() {
        whenever(preferences.getLong("LAST_USED_LOCATION", NO_LOCATION_ID)).thenReturn(1L)
        assertEquals(usecase.lastUsedLocationId(), 1L)
    }

    @Test
    fun testSaveLastUsedLocation() {
        usecase.saveLastUsedLocationId(1L)
        verify(preferences).edit()
        verify(editor).putLong("LAST_USED_LOCATION", 1L)
        verify(editor).apply()
    }

}