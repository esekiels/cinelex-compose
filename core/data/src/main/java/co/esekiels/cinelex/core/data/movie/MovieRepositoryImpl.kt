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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val client: MovieClient,
    private val dao: MovieDao,
    private val userPreferencesDataSource: UserPreferencesDataSource,
    @param:Dispatcher(CinelexDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : MovieRepository {

    override fun fetchNowPlaying(): Flow<List<Movie>> =
        dao.fetchMovieListByCategoryFlow(ApiConstant.NOW_PLAYING).map { it.toDomain() }

    override fun fetchUpcoming(): Flow<List<Movie>> =
        dao.fetchMovieListByCategoryFlow(ApiConstant.UPCOMING).map { it.toDomain() }

    override fun fetchTopRated(): Flow<List<Movie>> =
        dao.fetchMovieListByCategoryFlow(ApiConstant.TOP_RATED).map { it.toDomain() }

    override fun fetchPopular(): Flow<List<Movie>> =
        dao.fetchMovieListByCategoryFlow(ApiConstant.POPULAR).map { it.toDomain() }

    override suspend fun refreshMovies() = withContext(ioDispatcher) {
        val language = language()
        val categories = listOf(
            ApiConstant.NOW_PLAYING,
            ApiConstant.UPCOMING,
            ApiConstant.TOP_RATED,
            ApiConstant.POPULAR,
        )
        for (category in categories) {
            when (val response = client.fetchMovies(category, language)) {
                is ApiResponse.Success -> {
                    dao.clearByCategory(category)
                    dao.saveMovies(response.body.results.toEntities(category))
                }
                is ApiResponse.NetworkError -> {
                    val cached = dao.fetchMovieListByCategory(category)
                    if (cached.isEmpty()) throw response.asException()
                }
                else -> throw response.asException()
            }
        }
    }

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
        val cached = dao.fetchMovieDetailsById(id)
        if (cached != null) return@withContext cached.toDomain()

        when (val response = client.fetchDetails(id, language())) {
            is ApiResponse.Success -> {
                dao.saveMovieDetails(response.body.toEntities())
                response.body
            }
            else -> throw response.asException()
        }
    }

	private suspend fun language() =
		Language.fromCode(userPreferencesDataSource.getLanguage()).tmdbCode
}
