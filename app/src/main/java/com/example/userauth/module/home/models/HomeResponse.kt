package com.example.userauth.module.home.models

import com.google.gson.annotations.SerializedName

data class HomeResponse(

    @field:SerializedName("message")
    val message: String? = null
)