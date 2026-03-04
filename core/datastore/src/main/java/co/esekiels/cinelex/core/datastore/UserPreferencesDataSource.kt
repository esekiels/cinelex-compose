/*
 * Cinelex
 * UserPreferencesDataSource
 *
 * Created by Esekiel Surbakti on 01/03/26
 */

package co.esekiels.cinelex.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import co.esekiels.cinelex.core.model.Language
import co.esekiels.cinelex.core.model.UiTheme
import co.esekiels.cinelex.core.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    val userPreferences: Flow<UserPreferences> = dataStore.data.map { preferences ->
        UserPreferences(
            language = preferences[LANGUAGE_KEY] ?: Language.ENGLISH.code,
            uiTheme = preferences[THEME_KEY]?.let { UiTheme.valueOf(it) } ?: UiTheme.FOLLOW_SYSTEM,
        )
    }

    suspend fun currentLanguage(): String =
        dataStore.data.first()[LANGUAGE_KEY] ?: Language.ENGLISH.code

    suspend fun setLanguage(code: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = code
        }
    }

    suspend fun setUiTheme(uiTheme: UiTheme) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = uiTheme.name
        }
    }

    private companion object {
        val LANGUAGE_KEY = stringPreferencesKey("language")
        val THEME_KEY = stringPreferencesKey("ui_theme")
    }
}
