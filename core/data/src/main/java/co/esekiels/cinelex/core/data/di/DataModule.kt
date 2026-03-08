/*
 * Cinelex
 * DataModule
 *
 * Created by Esekiel Surbakti on 05/03/26
 */

package co.esekiels.cinelex.core.data.di

import co.esekiels.cinelex.core.data.movie.MovieRepository
import co.esekiels.cinelex.core.data.movie.MovieRepositoryImpl
import co.esekiels.cinelex.core.data.user.UserDataRepository
import co.esekiels.cinelex.core.data.user.UserDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindMovieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    fun bindUserDataRepository(userDataRepositoryImpl: UserDataRepositoryImpl): UserDataRepository
}
