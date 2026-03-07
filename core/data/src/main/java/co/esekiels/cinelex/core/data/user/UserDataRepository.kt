/*
 * Cinelex
 * UserDataRepository
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.data.user

import co.esekiels.cinelex.core.model.Language
import co.esekiels.cinelex.core.model.UiTheme
import co.esekiels.cinelex.core.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userPreferences: Flow<UserPreferences>
    fun observeLanguage(): Flow<String>
    suspend fun setLanguage(language: Language)
    suspend fun setUiTheme(uiTheme: UiTheme)
}
