package com.example.userauth.module.login.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userauth.module.login.apiPresenter.LoginPresenter
import com.example.userauth.module.login.interfaces.LoginInterface
import com.example.userauth.module.login.models.LoginResponse
import com.example.userauth.network.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _mLoginResponse = MutableStateFlow<NetworkState<LoginResponse>>(NetworkState.Loading)
    val mLoginResponse: StateFlow<NetworkState<LoginResponse>> = _mLoginResponse

    // login user
    fun loginUser(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            val loginPresenter: LoginPresenter = LoginPresenter(object : LoginInterface {
                override fun onLoginResponse(response: NetworkState<LoginResponse>) {
                    _mLoginResponse.value = response
                }
            })

            loginPresenter.loginUser(email, password)
        }
    }
}