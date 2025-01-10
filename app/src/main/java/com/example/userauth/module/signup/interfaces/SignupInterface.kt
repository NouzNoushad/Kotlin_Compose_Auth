package com.example.userauth.module.signup.interfaces

import com.example.userauth.module.signup.models.SignupResponse
import com.example.userauth.network.NetworkState

interface SignupInterface {
    fun onSignupResponse(response: NetworkState<SignupResponse>)
}