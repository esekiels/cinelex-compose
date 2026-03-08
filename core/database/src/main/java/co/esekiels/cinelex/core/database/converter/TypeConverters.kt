/*
 * Cinelex
 * TypeConverters
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.database.converter

import androidx.room.TypeConverter
import co.esekiels.cinelex.core.model.Credits
import co.esekiels.cinelex.core.model.Genre
import co.esekiels.cinelex.core.model.VideoResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class GenreListConverter {
    @TypeConverter
    fun fromGenres(genres: List<Genre>): String = Json.encodeToString(genres)

    @TypeConverter
    fun toGenres(json: String): List<Genre> = Json.decodeFromString(json)
}

internal class CreditsConverter {
    @TypeConverter
    fun fromCredits(credits: Credits?): String? = credits?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toCredits(json: String?): Credits? = json?.let { Json.decodeFromString(it) }
}

internal class VideoResponseConverter {
    @TypeConverter
    fun fromVideos(videos: VideoResponse?): String? = videos?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toVideos(json: String?): VideoResponse? = json?.let { Json.decodeFromString(it) }
}
