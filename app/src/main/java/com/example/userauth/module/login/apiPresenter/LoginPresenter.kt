package com.example.userauth.module.login.apiPresenter

import com.example.userauth.common.printError
import com.example.userauth.module.login.interfaces.LoginInterface
import com.example.userauth.module.login.models.LoginResponse
import com.example.userauth.network.NetworkState
import com.example.userauth.network.ResponseCode
import com.example.userauth.network.RetrofitClient
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException

class LoginPresenter(val action: LoginInterface) {
    fun loginUser(
        email: String,
        password: String
    ) {
        action.onLoginResponse(NetworkState.Loading)

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("email", email)
            .addFormDataPart("password", password)
            .build()

        val apiResponseCall = RetrofitClient.instance.loginUser(requestBody)

        apiResponseCall.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                action.onLoginResponse(
                    if(response.isSuccessful){
                        val apiResponse: LoginResponse? = response.body()

                        NetworkState.Success(apiResponse)
                    } else NetworkState.Failure(
                        false,
                        response.code(),
                        response.errorBody(),
                        ResponseCode.checkApiResponse(response)
                    )
                )
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                printError(t)

                when (t) {
                    is UnknownHostException, is ConnectException -> {
                        NetworkState.Failure(
                            true,
                            null,
                            null,
                            ResponseCode.NETWORK_FAILURE
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