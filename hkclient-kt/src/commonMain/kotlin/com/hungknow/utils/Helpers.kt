package com.hungknow.utils

external fun encodeURIComponent(str: String): String

fun buildQueryString(parameters: Map<String, String>): String {
    val keys = parameters.keys.toList()
    if (keys.size === 0) {
        return ""
    }

    var queryBuilder = StringBuilder()
    queryBuilder.append("?")
    for (i in 0..keys.size) {
        val key = keys[i]
        queryBuilder.append(key)
        queryBuilder.append("=")
        val value = parameters[key]
        if (value != null) {
            queryBuilder.append(encodeURIComponent(value))
        }

        if (i < keys.size - 1) {
            queryBuilder.append("&")
        }
    }

    return queryBuilder.toString()
}

