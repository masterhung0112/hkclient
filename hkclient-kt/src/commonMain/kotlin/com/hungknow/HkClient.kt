package com.hungknow

import com.hungknow.models.UserProfile
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.core.Closeable
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

data class ClientResponse<T>(
//    val response: Response,
    val headers: Map<String, String>,
    val data: T
);

class HkClient(val httpClientEngine: HttpClientEngine): CoroutineScope, Closeable {
    val httpClient by lazy {
        HttpClient(httpClientEngine) {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }

    override val coroutineContext: CoroutineContext = httpClientEngine.coroutineContext + Job(httpClientEngine.coroutineContext[Job])

    fun createUser(user: UserProfile, token: String, inviteId: String, redirect: String) {
        //this.trackEvent('api', 'api_users_create');
        launch {
            withContext(coroutineContext) {
                doPostWithResponse<UserProfile>("", "")
            }
        }
    }

    /***
     * Client Helpers
     */
//    fun doGetWithResponse<T>(url: String, options: Options) {
//        // Call URL
////        const response = await fetch(url, this.getOptions(options));
//        this.httpClient.get<T>(url)
////        const headers = parseAndMergeNestedHeaders(response.headers);
//
//    }

    suspend inline fun <reified T> doPostWithResponse(urlPath: String, bodyString: String) {
        // Call URL
//        const response = await fetch(url, this.getOptions(options));
                val message = this.httpClient.post<T> {
                    url(urlPath)
                    contentType(ContentType.Application.Json)
                    body = bodyString
        }
//        const headers = parseAndMergeNestedHeaders(response.headers);

    }

    override fun close() {

    }
}