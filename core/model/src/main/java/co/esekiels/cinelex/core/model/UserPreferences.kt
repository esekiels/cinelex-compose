/*
 * Cinelex
 * UserPreferences
 *
 * Created by Esekiel Surbakti on 01/03/26
 */

package co.esekiels.cinelex.core.model

data class UserPreferences(
    val language: String = Language.ENGLISH.code,
    val uiTheme: UiTheme = UiTheme.FOLLOW_SYSTEM,
)

enum class Language(val code: String) {
    ENGLISH("en"),
    INDONESIAN("in"),
    ;

    companion object {
        fun fromCode(code: String): Language =
            entries.firstOrNull { it.code == code } ?: ENGLISH
    }
}

enum class UiTheme {
    FOLLOW_SYSTEM,
    DARK,
    LIGHT,
}
