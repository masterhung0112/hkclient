package com.hungknow.utils

import kotlin.math.floor
import kotlin.random.Random

val HTTP_UNAUTHORIZED = 401

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

class Helpers {
    companion object {
        fun generateId(): String {
            // implementation taken from http://stackoverflow.com/a/2117523
            var id = "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx"
            val regexStr = "(x|y)".toRegex()
            id = regexStr.replace(id) { matchResult ->
                val r = floor(Random.nextDouble() * 16);
                var v = r.toInt()

                if (matchResult.value === "x") {
                } else {
                    v = v and 0x3 or 0x8
                }

                v.toString(16)
            }
            return id;
        }
    }
}