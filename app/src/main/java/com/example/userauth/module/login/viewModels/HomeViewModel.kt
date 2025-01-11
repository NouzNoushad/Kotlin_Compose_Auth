package com.example.userauth.module.login.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userauth.module.login.apiPresenter.HomePresenter
import com.example.userauth.module.login.interfaces.HomeInterface
import com.example.userauth.module.login.models.HomeResponse
import com.example.userauth.network.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _mHomeResponse = MutableStateFlow<NetworkState<HomeResponse>>(NetworkState.Loading)
    val mHomeResponse = _mHomeResponse

    fun checkToken(
        token: String
    ) {
        viewModelScope.launch {
            val homePresenter: HomePresenter = HomePresenter(object : HomeInterface {
                override fun onHomeResponse(response: NetworkState<HomeResponse>) {
                    _mHomeResponse.value = response
                }
            })
            homePresenter.checkToken(token)
        }
    }
}