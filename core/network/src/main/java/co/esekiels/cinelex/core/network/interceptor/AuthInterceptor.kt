/*
 * Cinelex
 * AuthInterceptor
 *
 * Created by Esekiel Surbakti on 04/03/26
 */

package co.esekiels.cinelex.core.network.interceptor

import co.esekiels.cinelex.core.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(): Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		val request = chain.request().newBuilder()
			.addHeader("Authorization", "Bearer ${BuildConfig.TOKEN}")
			.addHeader("Accept", "application/json")
			.build()
		return chain.proceed(request)
	}
}
