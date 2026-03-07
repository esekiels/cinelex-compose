/*
 * Cinelex
 * ApiAbstractTest
 *
 * Created by Esekiel Surbakti on 07/03/26
 */

package co.esekiels.cinelex.core.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import co.esekiels.cinelex.core.testing.MainCoroutinesRule
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.nio.charset.StandardCharsets

@RunWith(JUnit4::class)
abstract class ApiAbstractTest<T> {

    @Rule
    @JvmField
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    lateinit var mockWebServer: MockWebServer

    val json: Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Before
    fun mockServer() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun stopServer() {
        mockWebServer.shutdown()
    }

    fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, emptyMap())
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String>) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)))
    }

    fun createService(clazz: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(clazz)
    }
}
