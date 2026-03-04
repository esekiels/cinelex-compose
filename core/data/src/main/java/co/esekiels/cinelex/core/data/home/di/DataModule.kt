/*
 * Cinelex
 * DataModule
 *
 * Created by Esekiel Surbakti on 05/03/26
 */

package co.esekiels.cinelex.core.data.home.di

import co.esekiels.cinelex.core.data.home.HomeRepository
import co.esekiels.cinelex.core.data.home.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {
	
	@Binds
	abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
}
