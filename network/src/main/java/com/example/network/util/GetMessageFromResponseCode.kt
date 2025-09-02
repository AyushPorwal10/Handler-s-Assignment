package com.example.network.util

import android.health.connect.GetMedicalDataSourcesRequest

fun GetMessageFromResponseCode(code: Int): String {
    return when (code) {
        401 -> "Unauthorized Check Your API KEY"
        402 -> "API KEY LIMIT EXCEEDED"
        else -> "Unexpected error: Code $code"
    }
}
