/*
 * Cinelex
 * UserPreferencesSerializer
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class UserPreferencesSerializer @Inject constructor() : Serializer<UserPreferencesProto> {

    override val defaultValue: UserPreferencesProto = UserPreferencesProto()

    override suspend fun readFrom(input: InputStream): UserPreferencesProto =
        try {
            UserPreferencesProto.ADAPTER.decode(input)
        } catch (e: Exception) {
            throw CorruptionException("Cannot read proto.", e)
        }

    override suspend fun writeTo(t: UserPreferencesProto, output: OutputStream) {
        UserPreferencesProto.ADAPTER.encode(output, t)
    }
}
