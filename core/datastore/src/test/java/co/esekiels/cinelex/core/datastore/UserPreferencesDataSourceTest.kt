package co.esekiels.cinelex.core.datastore

import co.esekiels.cinelex.core.model.Language
import co.esekiels.cinelex.core.model.UiTheme
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserPreferencesDataSourceTest {

    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var subject: UserPreferencesDataSource

    @Before
    fun setup() {
        subject = UserPreferencesDataSource(
            dataStore = InMemoryDataStore(UserPreferencesProto()),
        )
    }

    @Test
    fun shouldLanguageIsEnglishByDefault() = testScope.runTest {
        assertEquals(Language.ENGLISH.code, subject.getLanguage())
    }

    @Test
    fun shouldThemeIsFollowSystemByDefault() = testScope.runTest {
        assertEquals(UiTheme.FOLLOW_SYSTEM, subject.getUserPreferences().uiTheme)
    }

    @Test
    fun shouldLanguageIsUpdatedWhenSet() = testScope.runTest {
        subject.setLanguage(Language.INDONESIAN.code)
        assertEquals(Language.INDONESIAN.code, subject.getLanguage())
    }

    @Test
    fun shouldThemeIsDarkWhenSet() = testScope.runTest {
        subject.setUiTheme(UiTheme.DARK)
        assertEquals(UiTheme.DARK, subject.getUserPreferences().uiTheme)
    }

    @Test
    fun shouldThemeIsLightWhenSet() = testScope.runTest {
        subject.setUiTheme(UiTheme.LIGHT)
        assertEquals(UiTheme.LIGHT, subject.getUserPreferences().uiTheme)
    }
}
