/*
 * Cinelex
 * GenreRepository
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.data.genre

import co.esekiels.cinelex.core.model.Genre
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
	fun fetchGenres(): Flow<List<Genre>>
	suspend fun refreshGenres()
}
