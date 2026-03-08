/*
 * Cinelex
 * GenreClient
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.network.service

import co.esekiels.cinelex.core.network.ApiResponse
import co.esekiels.cinelex.core.network.model.GenreResponse
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GenreClient @Inject constructor(
	private val service: GenreService,
	private val json: Json
) {
	
	suspend fun fetchGenres(language: String = "en"): ApiResponse<GenreResponse> =
		ApiResponse.safeApiCall(json) { service.fetchGenres(language) }
}
