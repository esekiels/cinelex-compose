/*
 * Cinelex
 * MovieRepository
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.data.movie

import co.esekiels.cinelex.core.model.Movie
import co.esekiels.cinelex.core.model.MovieDetails
import kotlinx.coroutines.flow.Flow

data class SearchResult(
    val movies: List<Movie>,
    val totalPages: Int,
)

interface MovieRepository {
    fun fetchNowPlaying(): Flow<List<Movie>>
    fun fetchUpcoming(): Flow<List<Movie>>
    fun fetchTopRated(): Flow<List<Movie>>
    fun fetchPopular(): Flow<List<Movie>>
    suspend fun refreshMovies()
    suspend fun searchMovies(query: String, page: Int = 1): SearchResult
    suspend fun fetchMovieDetails(id: Int): MovieDetails
}
