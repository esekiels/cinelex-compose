/*
 * Cinelex
 * MovieService
 *
 * Created by Esekiel Surbakti on 04/03/26
 */

package co.esekiels.cinelex.core.network.service

import co.esekiels.cinelex.core.model.MovieDetails
import co.esekiels.cinelex.core.network.ApiConstant
import co.esekiels.cinelex.core.network.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    
    @GET("{category}")
    suspend fun fetchMovies(
        @Path("category") category: String,
        @Query("language") language: String = "en",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET(ApiConstant.DETAILS)
    suspend fun fetchDetails(
        @Path("id") id: Int,
        @Query("language") language: String = "en",
        @Query("append_to_response") appendToResponse: String = "videos,credits"
    ): Response<MovieDetails>
}
