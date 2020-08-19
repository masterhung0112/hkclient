package com.hungknow.models

import kotlinx.serialization.Serializable

//@Serializable
data class IntlContent(
    val id: String = "",
    val defaultMessage: String = "",
    val values: Any? = null
)

//@Serializable
data class ServerError(
    val server_error_id: String? = null,
    val stack: String? = null,
    val intl: IntlContent? = null,
    val message: String = "",
    val status_code: Int = 0,
    val url: String? = null
)

//@Serializable
class ClientError: Throwable {
    var url: String?
    var intl: IntlContent? = null
    var server_error_id: String?
    var status_code: Int

//    constructor(baseUrl: String, data: ServerError): super(data.message + ": " + cleanUrlForLogging(baseUrl, data.url || "")) {
    constructor(baseUrl: String, data: ServerError): super(data.message + ": " + baseUrl + data.url) {
        this.url = data.url;
        this.intl = data.intl;
        this.server_error_id = data.server_error_id;
        this.status_code = data.status_code;
    }
}