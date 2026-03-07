package co.esekiels.cinelex.core.datastore

import androidx.datastore.core.CorruptionException
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class UserPreferencesSerializerTest {

    private val testScope = TestScope(UnconfinedTestDispatcher())

    private val subject = UserPreferencesSerializer()

    @Test
    fun defaultValueIsEmpty() {
        assertEquals(UserPreferencesProto(), subject.defaultValue)
    }

    @Test
    fun writingAndReadingUserPreferencesOutputsCorrectValue() = testScope.runTest {
        val proto = UserPreferencesProto(language = "en", ui_theme = "DARK")

        val outputStream = ByteArrayOutputStream()
        subject.writeTo(proto, outputStream)

        val inputStream = ByteArrayInputStream(outputStream.toByteArray())
        val result = subject.readFrom(inputStream)

        assertEquals(proto, result)
    }

    @Test(expected = CorruptionException::class)
    fun invalidUserPreferencesThrowsCorruptionException() = testScope.runTest {
        subject.readFrom(ByteArrayInputStream(byteArrayOf(0x7F, 0x7F, 0x7F, 0x7F)))
    }
}
