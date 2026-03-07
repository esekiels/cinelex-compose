/*
 * Cinelex
 * UserPreferencesDataSource
 *
 * Created by Esekiel Surbakti on 01/03/26
 */

package co.esekiels.cinelex.core.datastore

import androidx.datastore.core.DataStore
import co.esekiels.cinelex.core.model.Language
import co.esekiels.cinelex.core.model.UiTheme
import co.esekiels.cinelex.core.model.UserPreferences
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<UserPreferencesProto>,
) {
    val data get() = dataStore.data

    suspend fun getUserPreferences(): UserPreferences {
        val proto = dataStore.data.first()
        return UserPreferences(
            language = proto.language.ifEmpty { Language.ENGLISH.code },
            uiTheme = proto.ui_theme.toUiTheme(),
        )
    }

    suspend fun getLanguage(): String {
        val proto = dataStore.data.first()
        return proto.language.ifEmpty { Language.ENGLISH.code }
    }

    suspend fun setLanguage(code: String) {
        dataStore.updateData { it.copy(language = code) }
    }

    suspend fun setUiTheme(uiTheme: UiTheme) {
        dataStore.updateData { it.copy(ui_theme = uiTheme.name) }
    }
}

private fun String.toUiTheme(): UiTheme =
    if (isEmpty()) UiTheme.FOLLOW_SYSTEM
    else try { UiTheme.valueOf(this) } catch (_: Exception) { UiTheme.FOLLOW_SYSTEM }
