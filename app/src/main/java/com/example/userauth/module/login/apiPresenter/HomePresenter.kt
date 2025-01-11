package com.example.userauth.module.login.apiPresenter

import com.example.userauth.module.login.interfaces.HomeInterface
import com.example.userauth.module.login.models.HomeResponse
import com.example.userauth.network.NetworkState
import com.example.userauth.network.ResponseCode
import com.example.userauth.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException

class HomePresenter(val action: HomeInterface) {
    fun checkToken(
        token: String
    ) {
        action.onHomeResponse(NetworkState.Loading)

        val bearerToken: String = "Bearer $token"
        val apiResponseCall = RetrofitClient.instance.home(bearerToken)

        apiResponseCall.enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                action.onHomeResponse(
                    if (response.isSuccessful) {
                        val apiResponse: HomeResponse? = response.body()

                        NetworkState.Success(apiResponse)
                    } else NetworkState.Failure(
                        false,
                        response.code(),
                        response.errorBody(),
                        ResponseCode.checkApiResponse(response)
                    )
                )
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                when (t) {
                    is UnknownHostException, is ConnectException -> {
                        NetworkState.Failure(
                            true,
                            null,
                            null,
                            ResponseCode.NETWORK_FAILURE,
                        )
                    } else -> {
                        NetworkState.Failure(
                            false,
                            null,
                            null,
                            ResponseCode.CONVERSION_FAILURE
                        )
                    }
                }
            }
        })
    }
}