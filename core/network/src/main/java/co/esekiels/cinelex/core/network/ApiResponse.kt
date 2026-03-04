/*
 * Cinelex
 * ApiResponse
 *
 * Created by Esekiel Surbakti on 04/03/26
 */

package co.esekiels.cinelex.core.network

import co.esekiels.cinelex.core.common.CinelexException
import co.esekiels.cinelex.core.common.ErrorConstant
import co.esekiels.cinelex.core.network.model.ErrorResponse
import kotlinx.serialization.json.Json
import retrofit2.Response
import java.io.IOException

sealed class ApiResponse<out T : Any> {
	
	data class Success<T : Any>(val body: T) : ApiResponse<T>()
	
	data class ApiError(val body: ErrorResponse?, val code: Int) : ApiResponse<Nothing>()
	
	data class NetworkError(val error: IOException) : ApiResponse<Nothing>()
	
	data class UnknownError(val error: CinelexException) : ApiResponse<Nothing>()
	
	fun asException(): CinelexException = when (this) {
		is ApiError -> mapApiError(body, code)
		is NetworkError -> CinelexException(
			code = ErrorConstant.NETWORK_ERROR,
			message = "Please check your internet connection and try again.",
		)
		is UnknownError -> error
		is Success -> error("Cannot convert success response to exception")
	}
	
	companion object {
		
		suspend fun <T : Any> safeApiCall(
			json: Json,
			call: suspend () -> Response<T>,
		): ApiResponse<T> {
			return try {
				val response = call()
				
				if (response.isSuccessful) {
					val body = response.body()
					if (body != null) {
						Success(body)
					} else {
						UnknownError(
							CinelexException(ErrorConstant.HTTP_EMPTY_BODY, "Response body was empty or null"),
						)
					}
				} else {
					ApiError(parseErrorBody(json, response), response.code())
				}
			} catch (e: IOException) {
				NetworkError(e)
			} catch (e: Exception) {
				UnknownError(
					CinelexException(
						ErrorConstant.UNKNOWN_ERROR,
						e.message ?: "Unknown error during network request"
					),
				)
			}
		}
		
		private fun <T> parseErrorBody(json: Json, response: Response<T>): ErrorResponse? {
			val errorString = response.errorBody()?.string() ?: return null
			if (errorString.isEmpty()) return null
			
			return try {
				json.decodeFromString<ErrorResponse>(errorString)
			} catch (e: Exception) {
				null
			}
		}
		
		private fun mapApiError(body: ErrorResponse?, code: Int): CinelexException {
			val fallback = body?.message ?: "An unexpected error occurred."
			return when (code) {
				401 -> CinelexException(ErrorConstant.HTTP_UNAUTHORIZED, body?.message ?: "Session has expired")
				403 -> CinelexException(ErrorConstant.HTTP_FORBIDDEN, "You don't have permission to access this resource.")
				408, 504 -> CinelexException(ErrorConstant.HTTP_TIMEOUT, "The request timed out. Please try again.")
				in 500..599 -> CinelexException(ErrorConstant.UNKNOWN_ERROR, "Server error. Please try again later.")
				else -> CinelexException(ErrorConstant.UNKNOWN_ERROR, fallback)
			}
		}
	}
}
