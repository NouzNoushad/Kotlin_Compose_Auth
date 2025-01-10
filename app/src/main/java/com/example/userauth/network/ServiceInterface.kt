package com.example.userauth.network

import com.example.userauth.module.home.models.HomeResponse
import com.example.userauth.module.login.models.LoginResponse
import com.example.userauth.module.signup.models.SignupResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ServiceInterface {

    @POST(Endpoints.LOGIN)
    fun loginUser(@Body map: RequestBody): Call<LoginResponse>

    @POST(Endpoints.SIGNUP)
    fun signupUser(@Body map: RequestBody): Call<SignupResponse>

    @GET(Endpoints.HOME)
    fun home(@Header("Authorization Bearer") token: String): Call<HomeResponse>
}