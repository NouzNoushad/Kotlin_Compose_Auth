package com.example.userauth.module.login.interfaces

import com.example.userauth.module.login.models.LoginResponse
import com.example.userauth.network.NetworkState

interface LoginInterface {
    fun onLoginResponse(response: NetworkState<LoginResponse>)
}