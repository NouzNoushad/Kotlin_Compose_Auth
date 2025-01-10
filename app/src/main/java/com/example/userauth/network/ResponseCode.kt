package com.example.userauth.network

import retrofit2.Response

object ResponseCode {
    const val NETWORK_FAILURE = "No internet connection"
    const val CONVERSION_FAILURE = "Conversion Error"

    private const val STS_400 = "Bad Request!"
    private const val STS_401 = "Unauthorized!"
    private const val STS_403 = "Forbidden!"
    private const val STS_404 = "Not Found!"
    private const val STS_408 = "Request Timed Out!"
    private const val STS_500 = "Internal Server Error!"
    private const val STS_502 = "Bad Gateway!"
    private const val STS_503 = "Service Unavailable!"
    private const val STS_504 = "Gateway Timeout!"
    private const val STS_DEFAULT = "Something went wrong"

    fun checkApiResponse(response: Response<*>): String {
        return when (response.code()) {
            400 -> STS_400
            401 -> STS_401
            403 -> STS_403
            404 -> STS_404
            408 -> STS_408
            500 -> STS_500
            502 -> STS_502
            503 -> STS_503
            504 -> STS_504
            else -> STS_DEFAULT
        }
    }
}