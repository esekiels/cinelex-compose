/*
 * Cinelex
 * HomeRepository
 *
 * Created by Esekiel Surbakti on 04/03/26
 */

package co.esekiels.cinelex.core.data.home

import co.esekiels.cinelex.core.model.Movie
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
	suspend fun fetchNowPlaying(): List<Movie>
	suspend fun fetchUpcoming(): List<Movie>
	suspend fun fetchTopRated(): List<Movie>
	suspend fun fetchPopular(): List<Movie>
	fun observeLanguage(): Flow<String>
}
