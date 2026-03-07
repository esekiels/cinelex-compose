/*
 * Cinelex
 * DatabaseModule
 *
 * Created by Esekiel Surbakti on 04/03/26
 */

package co.esekiels.cinelex.core.database.di

import android.content.Context
import androidx.room.Room
import co.esekiels.cinelex.core.database.CinelexDatabase
import co.esekiels.cinelex.core.database.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideCinelexDatabase(@ApplicationContext context: Context): CinelexDatabase =
        Room.databaseBuilder(
            context,
            CinelexDatabase::class.java,
            "cinelex-database"
        ).build()
    
    @Provides
    fun provideMovieDao(database: CinelexDatabase): MovieDao = database.movieDao()
}
