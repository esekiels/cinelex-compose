/*
 * Cinelex
 * MovieRepositoryImpl
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.data.movie

import co.esekiels.cinelex.core.common.di.CinelexDispatchers
import co.esekiels.cinelex.core.common.di.Dispatcher
import co.esekiels.cinelex.core.database.dao.MovieDao
import co.esekiels.cinelex.core.database.entity.mapper.toDomain
import co.esekiels.cinelex.core.database.entity.mapper.toEntities
import co.esekiels.cinelex.core.datastore.UserPreferencesDataSource
import co.esekiels.cinelex.core.model.Language
import co.esekiels.cinelex.core.model.Movie
import co.esekiels.cinelex.core.model.MovieDetails
import co.esekiels.cinelex.core.network.ApiConstant
import co.esekiels.cinelex.core.network.ApiResponse
import co.esekiels.cinelex.core.network.service.MovieClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val client: MovieClient,
    private val dao: MovieDao,
    private val userPreferencesDataSource: UserPreferencesDataSource,
    @param:Dispatcher(CinelexDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : MovieRepository {

    override suspend fun fetchNowPlaying(): List<Movie> = fetchMovies(ApiConstant.NOW_PLAYING)
    override suspend fun fetchUpcoming(): List<Movie> = fetchMovies(ApiConstant.UPCOMING)
    override suspend fun fetchTopRated(): List<Movie> = fetchMovies(ApiConstant.TOP_RATED)
    override suspend fun fetchPopular(): List<Movie> = fetchMovies(ApiConstant.POPULAR)

    override suspend fun searchMovies(query: String, page: Int): SearchResult =
        withContext(ioDispatcher) {
            when (val response = client.searchMovies(query, language(), page)) {
                is ApiResponse.Success -> SearchResult(
                    movies = response.body.results,
                    totalPages = response.body.totalPages,
                )
                else -> throw response.asException()
            }
        }

    override suspend fun fetchMovieDetails(id: Int): MovieDetails = withContext(ioDispatcher) {
        when (val response = client.fetchDetails(id, language())) {
            is ApiResponse.Success -> {
                dao.saveMovieDetails(response.body.toEntities())
                response.body
            }
            is ApiResponse.NetworkError -> {
                val cached = dao.fetchMovieDetailsById(id)
	            cached?.toDomain() ?: throw response.asException()
            }
            else -> throw response.asException()
        }
    }

    private suspend fun fetchMovies(category: String): List<Movie> = withContext(ioDispatcher) {
        when (val response = client.fetchMovies(category, language())) {
            is ApiResponse.Success -> {
                dao.clearByCategory(category)
                dao.saveMovies(response.body.results.toEntities(category))
                dao.fetchMovieListByCategory(category).toDomain()
            }
            is ApiResponse.NetworkError -> {
                val cached = dao.fetchMovieListByCategory(category)
                if (cached.isNotEmpty()) cached.toDomain()
                else throw response.asException()
            }
            else -> throw response.asException()
        }
    }
	
	private suspend fun language() =
		Language.fromCode(userPreferencesDataSource.getLanguage()).tmdbCode
}
