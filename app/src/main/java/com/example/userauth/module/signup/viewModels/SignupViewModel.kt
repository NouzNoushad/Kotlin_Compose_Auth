package com.example.userauth.module.signup.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userauth.module.signup.apiPresenter.SignupPresenter
import com.example.userauth.module.signup.interfaces.SignupInterface
import com.example.userauth.module.signup.models.SignupResponse
import com.example.userauth.network.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignupViewModel() : ViewModel() {
    private val _mSignupResponse = MutableStateFlow<NetworkState<SignupResponse>>(NetworkState.Loading)

    val mSignupResponse: StateFlow<NetworkState<SignupResponse>> = _mSignupResponse

    // signup user
    fun signupUser(
        name: String,
        email: String,
        password: String,
    ){
        viewModelScope.launch {
            val signupPresenter: SignupPresenter = SignupPresenter(object : SignupInterface {
                override fun onSignupResponse(response: NetworkState<SignupResponse>) {
                    _mSignupResponse.value = response
                }
            })
            signupPresenter.signupUser(name, email, password)
        }
    }
}