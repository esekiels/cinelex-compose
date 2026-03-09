/*
 * Cinelex
 * MovieClient
 *
 * Created by Esekiel Surbakti on 04/03/26
 */

package co.esekiels.cinelex.core.network.service

import co.esekiels.cinelex.core.model.MovieDetails
import co.esekiels.cinelex.core.network.ApiResponse
import co.esekiels.cinelex.core.network.model.MovieResponse
import kotlinx.serialization.json.Json
import javax.inject.Inject

class MovieClient @Inject constructor(
    private val service: MovieService,
    private val json: Json
) {
    
    suspend fun fetchMovies(category: String, language: String = "en"): ApiResponse<MovieResponse> =
        ApiResponse.safeApiCall(json) { service.fetchMovies(category, language) }

    suspend fun searchMovies(query: String, language: String = "en", page: Int = 1): ApiResponse<MovieResponse> =
        ApiResponse.safeApiCall(json) { service.searchMovies(query, language, page) }

    suspend fun fetchDetails(id: Int, language: String = "en"): ApiResponse<MovieDetails> =
        ApiResponse.safeApiCall(json) { service.fetchDetails(id, language) }
}
