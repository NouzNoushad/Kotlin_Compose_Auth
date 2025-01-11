package com.example.userauth.module.login.models

import com.google.gson.annotations.SerializedName

data class HomeResponse(

    @field:SerializedName("message")
    val message: String? = null
)