/*
 * Cinelex
 * MovieRepository
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.data.movie

import co.esekiels.cinelex.core.model.Movie
import co.esekiels.cinelex.core.model.MovieDetails

interface MovieRepository {
    suspend fun fetchNowPlaying(): List<Movie>
    suspend fun fetchUpcoming(): List<Movie>
    suspend fun fetchTopRated(): List<Movie>
    suspend fun fetchPopular(): List<Movie>
    suspend fun fetchMovieDetails(id: Int): MovieDetails
}
