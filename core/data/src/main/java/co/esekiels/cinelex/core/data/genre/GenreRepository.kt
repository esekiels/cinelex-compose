/*
 * Cinelex
 * GenreRepository
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.data.genre

import co.esekiels.cinelex.core.model.Genre

interface GenreRepository {
	suspend fun fetchGenres(): List<Genre>
}
