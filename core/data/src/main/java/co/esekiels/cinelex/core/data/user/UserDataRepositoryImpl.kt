/*
 * Cinelex
 * UserDataRepositoryImpl
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.data.user

import co.esekiels.cinelex.core.datastore.UserPreferencesDataSource
import co.esekiels.cinelex.core.model.Language
import co.esekiels.cinelex.core.model.UiTheme
import co.esekiels.cinelex.core.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val dataSource: UserPreferencesDataSource,
) : UserDataRepository {

    override val userPreferences: Flow<UserPreferences> =
        dataSource.data.map { proto ->
            UserPreferences(
                language = proto.language.ifEmpty { Language.ENGLISH.code },
                uiTheme = proto.ui_theme.toUiTheme(),
            )
        }

    override fun observeLanguage(): Flow<String> =
        userPreferences
            .map { Language.fromCode(it.language).tmdbCode }
            .distinctUntilChanged()

    override suspend fun setLanguage(language: Language) {
        dataSource.setLanguage(language.code)
    }

    override suspend fun setUiTheme(uiTheme: UiTheme) {
        dataSource.setUiTheme(uiTheme)
    }
}

private fun String.toUiTheme(): UiTheme =
    if (isEmpty()) UiTheme.FOLLOW_SYSTEM
    else try { UiTheme.valueOf(this) } catch (_: Exception) { UiTheme.FOLLOW_SYSTEM }
