/*
 * Cinelex
 * UserDataRepositoryImplTest
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.data.user

import androidx.datastore.core.DataStore
import co.esekiels.cinelex.core.datastore.UserPreferencesDataSource
import co.esekiels.cinelex.core.datastore.UserPreferencesProto
import co.esekiels.cinelex.core.model.Language
import co.esekiels.cinelex.core.model.UiTheme
import co.esekiels.cinelex.core.model.UserPreferences
import co.esekiels.cinelex.core.testing.MainCoroutinesRule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserDataRepositoryImplTest {

    private lateinit var repository: UserDataRepository
    private lateinit var preferencesDataSource: UserPreferencesDataSource

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        preferencesDataSource = UserPreferencesDataSource(
            dataStore = InMemoryDataStore(UserPreferencesProto()),
        )
        repository = UserDataRepositoryImpl(preferencesDataSource)
    }

    @Test
    fun shouldReturnDefaultUserPreferences() = runTest {
        assertEquals(
            UserPreferences(
                language = Language.ENGLISH.code,
                uiTheme = UiTheme.FOLLOW_SYSTEM,
            ),
            repository.userPreferences.first(),
        )
    }

    @Test
    fun shouldUpdateUiTheme() = runTest {
        repository.setUiTheme(UiTheme.DARK)

        assertEquals(
            UiTheme.DARK,
            repository.userPreferences.map { it.uiTheme }.first(),
        )
    }

    @Test
    fun shouldUpdateLanguage() = runTest {
        repository.setLanguage(Language.INDONESIAN)

        assertEquals(
            Language.INDONESIAN.code,
            repository.userPreferences.map { it.language }.first(),
        )
    }
}

private class InMemoryDataStore<T>(initialValue: T) : DataStore<T> {
    override val data = MutableStateFlow(initialValue)
    override suspend fun updateData(transform: suspend (T) -> T): T =
        data.updateAndGet { transform(it) }
}
