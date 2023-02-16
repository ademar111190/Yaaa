package ademar.yaaa.usecase

import ademar.yaaa.Dummies
import ademar.yaaa.db.AppDatabase
import ademar.yaaa.db.location.LocationDao
import ademar.yaaa.usecase.mapper.LocationMapper
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Resources
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
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
        whenever(editor.putString(any(), any())).thenReturn(editor)
        usecase = Preferences(context)
    }

    @Test
    fun testLastUsedLocation() {
        whenever(preferences.getString("LAST_USED_LOCATION", null)).thenReturn("location")
        assertEquals(usecase.lastUsedLocation(), "location")
    }

    @Test
    fun testSaveLastUsedLocation() {
        usecase.saveLastUsedLocation("location")
        verify(preferences).edit()
        verify(editor).putString("LAST_USED_LOCATION", "location")
        verify(editor).apply()
    }

}