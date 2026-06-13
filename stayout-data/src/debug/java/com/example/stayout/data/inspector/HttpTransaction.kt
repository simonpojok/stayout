package com.example.stayout.data.inspector

data class HttpTransaction(
    val id: Long = System.nanoTime(),
    val method: String,
    val host: String,
    val path: String,
    val url: String,
    val requestHeaders: List<Pair<String, String>>,
    val requestBody: String?,
    val responseCode: Int?,
    val responseMessage: String?,
    val responseHeaders: List<Pair<String, String>>,
    val responseBody: String?,
    val durationMs: Long,
    val requestedAt: Long = System.currentTimeMillis(),
    val error: String? = null,
) {
    val isSuccess: Boolean get() = responseCode != null && responseCode in 200..299
    val isError: Boolean get() = (responseCode != null && responseCode >= 400) || error != null
}
