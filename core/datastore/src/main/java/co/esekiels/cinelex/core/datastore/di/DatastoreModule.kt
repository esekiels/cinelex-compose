/*
 * Cinelex
 * DatastoreModule
 *
 * Created by Esekiel Surbakti on 01/03/26
 */

package co.esekiels.cinelex.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import co.esekiels.cinelex.core.datastore.UserPreferencesProto
import co.esekiels.cinelex.core.datastore.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(
        @ApplicationContext context: Context,
        serializer: UserPreferencesSerializer,
    ): DataStore<UserPreferencesProto> =
        DataStoreFactory.create(
            serializer = serializer,
        ) {
            context.dataStoreFile("user_preferences.pb")
        }
}
