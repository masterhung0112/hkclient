package com.hungknow

import com.hungknow.models.UserProfile
import com.hungknow.utils.buildQueryString
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
import kotlin.js.JsName
import kotlin.jvm.JvmName

data class ClientResponse<T>(
//    val response: Response,
        val headers: Map<String, String>,
        val data: T
);

class HkClient(val httpClientEngine: HttpClientEngine?) : CoroutineScope, Closeable {
    var token = ""

    val httpClient by lazy {
        if (httpClientEngine != null) {
            HttpClient(httpClientEngine) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer()
                }
            }
        } else {
            HttpClient() {
                install(JsonFeature) {
                    serializer = KotlinxSerializer()
                }
            }
        }
    }

    override val coroutineContext: CoroutineContext = httpClient.coroutineContext + Job(httpClient.coroutineContext[Job])

    @JsName("createUser")
    fun createUser(user: UserProfile, token: String? = null, inviteId: String? = null, redirect: String? = null, completionHandler: (Result<UserProfile>).() -> Unit
    ) {
        //this.trackEvent('api', 'api_users_create');

        val queryParams = hashMapOf<String, String>()

        token?.let { queryParams.put("t", token) }
        inviteId?.let { queryParams.put("iid", inviteId) }
        redirect?.let { queryParams.put("r", redirect) }
        launch {
            doPost("${getUsersRoute()}${buildQueryString(queryParams)}", user, completionHandler)
        }
    }

    /*****
     * URL Path
     */
    var _url = ""
    val _urlVersion = "/api/v1"

    fun setUrl(url: String) {
        this._url = url;
    }

    fun getBaseRoute(): String {
        return "${this._url}${this._urlVersion}"
    }

    fun getUsersRoute(): String {
        return "${this.getBaseRoute()}/users"
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
    suspend inline fun <reified T> doPost(urlPath: String, bodyString: Any, completionHandler: (Result<T>).() -> Unit) {
        doPostWithResponse<T>(urlPath, bodyString, completionHandler)
    }

    suspend inline fun <reified T> doPostWithResponse(urlPath: String, bodyString: Any, completionHandler: (Result<T>).() -> Unit) {
        // Call URL
//        const response = await fetch(url, this.getOptions(options));
        val message = this.httpClient.post<T> {
            url(urlPath)
            contentType(ContentType.Application.Json)
            body = bodyString
        }

        completionHandler(Result.success(message))
//        const headers = parseAndMergeNestedHeaders(response.headers);

    }

    override fun close() {

    }
}