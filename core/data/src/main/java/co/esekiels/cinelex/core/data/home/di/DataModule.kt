/*
 * Cinelex
 * DataModule
 *
 * Created by Esekiel Surbakti on 05/03/26
 */

package co.esekiels.cinelex.core.data.home.di

import co.esekiels.cinelex.core.data.details.DetailsRepository
import co.esekiels.cinelex.core.data.details.DetailsRepositoryImpl
import co.esekiels.cinelex.core.data.home.HomeRepository
import co.esekiels.cinelex.core.data.home.HomeRepositoryImpl
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
    fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    fun bindDetailsRepository(detailsRepositoryImpl: DetailsRepositoryImpl): DetailsRepository

    @Binds
    fun bindUserDataRepository(userDataRepositoryImpl: UserDataRepositoryImpl): UserDataRepository
}
