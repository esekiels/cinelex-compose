/*
 * Cinelex
 * GenreService
 *
 * Created by Esekiel Surbakti on 08/03/26
 */

package co.esekiels.cinelex.core.network.service

import co.esekiels.cinelex.core.network.ApiConstant
import co.esekiels.cinelex.core.network.model.GenreResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreService {

    @GET(ApiConstant.GENRES)
    suspend fun fetchGenres(
        @Query("language") language: String = "en"
    ): Response<GenreResponse>
}
