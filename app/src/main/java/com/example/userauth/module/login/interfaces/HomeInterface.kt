package com.example.userauth.module.login.interfaces

import com.example.userauth.module.login.models.HomeResponse
import com.example.userauth.network.NetworkState

interface HomeInterface {
    fun onHomeResponse(response: NetworkState<HomeResponse>)
}