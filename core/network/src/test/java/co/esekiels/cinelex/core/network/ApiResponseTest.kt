/*
 * Cinelex
 * ApiResponseTest
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.network

import co.esekiels.cinelex.core.common.ErrorConstant
import co.esekiels.cinelex.core.network.model.ErrorResponse
import co.esekiels.cinelex.core.network.model.MovieResponse
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class ApiResponseTest {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Test
    fun shouldReturnSuccessResponse() = runTest {
        val movieResponse = MovieResponse(results = emptyList())
        val result = ApiResponse.safeApiCall(json) {
            Response.success(movieResponse)
        }
        assertTrue(result is ApiResponse.Success)
        assertEquals(movieResponse, (result as ApiResponse.Success).body)
    }

    @Test
    fun shouldReturnExceptionResponse() = runTest {
        val result = ApiResponse.safeApiCall<MovieResponse>(json) {
            throw IllegalStateException("Something went wrong")
        }
        assertTrue(result is ApiResponse.UnknownError)
        assertEquals(ErrorConstant.UNKNOWN_ERROR, (result as ApiResponse.UnknownError).error.code)
    }

    @Test
    fun shouldReturnUnauthorizedResponse() {
        val response = ApiResponse.ApiError(
            body = ErrorResponse(code = 7, message = "Invalid API key"),
            code = 401,
        )
        val exception = response.asException()
        assertEquals(ErrorConstant.HTTP_UNAUTHORIZED, exception.code)
    }
}
