/*
 * Cinelex
 * HomeRepositoryImpl
 *
 * Created by Esekiel Surbakti on 04/03/26
 */

package co.esekiels.cinelex.core.data.home

import co.esekiels.cinelex.core.common.di.CinelexDispatchers
import co.esekiels.cinelex.core.common.di.Dispatcher
import co.esekiels.cinelex.core.database.dao.MovieDao
import co.esekiels.cinelex.core.database.entity.mapper.toDomain
import co.esekiels.cinelex.core.database.entity.mapper.toEntities
import co.esekiels.cinelex.core.datastore.UserPreferencesDataSource
import co.esekiels.cinelex.core.model.Language
import co.esekiels.cinelex.core.model.Movie
import co.esekiels.cinelex.core.network.ApiConstant
import co.esekiels.cinelex.core.network.ApiResponse
import co.esekiels.cinelex.core.network.service.MovieClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
	private val client: MovieClient,
	private val dao: MovieDao,
	private val userPreferencesDataSource: UserPreferencesDataSource,
	@param:Dispatcher(CinelexDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : HomeRepository {

	override suspend fun fetchNowPlaying(): List<Movie> = fetchMovies(ApiConstant.NOW_PLAYING)
	override suspend fun fetchUpcoming(): List<Movie> = fetchMovies(ApiConstant.UPCOMING)
	override suspend fun fetchTopRated(): List<Movie> = fetchMovies(ApiConstant.TOP_RATED)
	override suspend fun fetchPopular(): List<Movie> = fetchMovies(ApiConstant.POPULAR)

	private suspend fun fetchMovies(category: String): List<Movie> = withContext(ioDispatcher) {
		val language = Language.fromCode(userPreferencesDataSource.currentLanguage()).tmdbCode
		when (val response = client.fetchMovies(category, language)) {
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

	override fun observeLanguage(): Flow<String> =
		userPreferencesDataSource.userPreferences
			.map { Language.fromCode(it.language).tmdbCode }
			.distinctUntilChanged()
}
