/*
 * Cinelex
 * GenreRepositoryImpl
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.data.genre

import co.esekiels.cinelex.core.common.di.CinelexDispatchers
import co.esekiels.cinelex.core.common.di.Dispatcher
import co.esekiels.cinelex.core.database.dao.GenreDao
import co.esekiels.cinelex.core.database.entity.mapper.toDomain
import co.esekiels.cinelex.core.database.entity.mapper.toEntities
import co.esekiels.cinelex.core.datastore.UserPreferencesDataSource
import co.esekiels.cinelex.core.model.Genre
import co.esekiels.cinelex.core.model.Language
import co.esekiels.cinelex.core.network.ApiResponse
import co.esekiels.cinelex.core.network.service.GenreClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
	private val client: GenreClient,
	private val dao: GenreDao,
	private val userPreferencesDataSource: UserPreferencesDataSource,
	@param:Dispatcher(CinelexDispatchers.IO) private val ioDispatchers: CoroutineDispatcher
): GenreRepository {
	
	override suspend fun fetchGenres(): List<Genre> = withContext(ioDispatchers) {
		val language = Language.fromCode(userPreferencesDataSource.getLanguage()).tmdbCode
		when (val response = client.fetchGenres(language)) {
			is ApiResponse.Success -> {
				dao.saveGenres(response.body.results.toEntities())
				response.body.results
			}
			is ApiResponse.NetworkError -> {
				val cached = dao.fetchGenres()
				if (!cached.isEmpty()) cached.toDomain() else throw  response.asException()
			}
			else -> throw response.asException()
		}
	}
}
