/*
 * Cinelex
 * DetailsRepositoryImpl
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.data.details

import co.esekiels.cinelex.core.common.di.CinelexDispatchers
import co.esekiels.cinelex.core.common.di.Dispatcher
import co.esekiels.cinelex.core.database.dao.MovieDetailsDao
import co.esekiels.cinelex.core.database.entity.mapper.toDomain
import co.esekiels.cinelex.core.database.entity.mapper.toEntity
import co.esekiels.cinelex.core.datastore.UserPreferencesDataSource
import co.esekiels.cinelex.core.model.Language
import co.esekiels.cinelex.core.model.MovieDetails
import co.esekiels.cinelex.core.network.ApiResponse
import co.esekiels.cinelex.core.network.service.MovieClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val client: MovieClient,
    private val dao: MovieDetailsDao,
    private val userPreferencesDataSource: UserPreferencesDataSource,
    @param:Dispatcher(CinelexDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : DetailsRepository {

    override suspend fun fetchMovieDetails(id: Int): MovieDetails = withContext(ioDispatcher) {
        val language = Language.fromCode(userPreferencesDataSource.getLanguage()).tmdbCode
        when (val response = client.fetchDetails(id, language)) {
            is ApiResponse.Success -> {
                dao.saveMovieDetails(response.body.toEntity())
                response.body
            }
            is ApiResponse.NetworkError -> {
                val cached = dao.fetchMovieDetailsById(id)
                if (cached != null) cached.toDomain()
                else throw response.asException()
            }
            else -> throw response.asException()
        }
    }
}
