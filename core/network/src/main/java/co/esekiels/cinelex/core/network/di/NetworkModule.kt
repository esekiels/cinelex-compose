/*
 * Cinelex
 * NetworkModule
 *
 * Created by Esekiel Surbakti on 04/03/26
 */

package co.esekiels.cinelex.core.network.di

import co.esekiels.cinelex.core.network.BuildConfig
import co.esekiels.cinelex.core.network.interceptor.AuthInterceptor
import co.esekiels.cinelex.core.network.service.MovieClient
import co.esekiels.cinelex.core.network.service.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
	
	@Provides
	@Singleton
	fun provideJson(): Json = Json {
		ignoreUnknownKeys = true
		coerceInputValues = true
	}
	
	@Provides
	@Singleton
	fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
		return OkHttpClient.Builder()
			.apply {
				addNetworkInterceptor(authInterceptor)
				if (BuildConfig.DEBUG) {
					this.addNetworkInterceptor(
						HttpLoggingInterceptor().apply {
							level = HttpLoggingInterceptor.Level.BODY
						}
					)
				}
			}
			.build()
	}
	
	@Provides
	@Singleton
	fun provideRetrofit(json: Json, okHttpClient: OkHttpClient): Retrofit {
		return Retrofit.Builder()
			.client(okHttpClient)
			.baseUrl(BuildConfig.BASE_URL)
			.addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
			.build()
	}
	
	@Provides
	@Singleton
	fun provideMovieService(retrofit: Retrofit): MovieService {
		return retrofit.create(MovieService::class.java)
	}
	
	@Provides
	@Singleton
	fun provideMovieClient(movieService: MovieService, json: Json): MovieClient {
		return MovieClient(movieService, json)
	}
}
