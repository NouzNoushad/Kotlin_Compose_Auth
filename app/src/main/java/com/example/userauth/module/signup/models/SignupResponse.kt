package com.example.userauth.module.signup.models

import com.google.gson.annotations.SerializedName

data class SignupResponse(

    @field:SerializedName("message")
    val message: String? = null
)