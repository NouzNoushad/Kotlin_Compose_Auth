package com.example.userauth.module.signup.apiPresenter

import com.example.userauth.common.printError
import com.example.userauth.module.signup.interfaces.SignupInterface
import com.example.userauth.module.signup.models.SignupResponse
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

class SignupPresenter(val action: SignupInterface) {
    fun signupUser(
        name: String,
        email: String,
        password: String,
    ) {
        action.onSignupResponse(NetworkState.Loading)

        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name", name)
            .addFormDataPart("email", email)
            .addFormDataPart("password", password)
            .build()

        val apiResponseCall = RetrofitClient.instance.signupUser(requestBody)

        apiResponseCall.enqueue(object : Callback<SignupResponse> {
            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {
                action.onSignupResponse(
                    if (response.isSuccessful) {
                        val apiResponse: SignupResponse? = response.body()

                        NetworkState.Success(apiResponse)
                    } else NetworkState.Failure(
                        false,
                        response.code(),
                        response.errorBody(),
                        ResponseCode.checkApiResponse(response)
                    )
                )
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                printError(t)
                action.onSignupResponse(
                    when (t) {
                        is UnknownHostException, is ConnectException -> {
                            NetworkState.Failure(
                                true,
                                null,
                                null,
                                ResponseCode.NETWORK_FAILURE
                            )
                        }
                        else -> {
                            NetworkState.Failure(
                                false,
                                null,
                                null,
                                ResponseCode.CONVERSION_FAILURE
                            )
                        }
                    }
                )
            }
        })
    }
}