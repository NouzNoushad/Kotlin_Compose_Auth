package com.example.userauth.common

import android.util.Log

fun printError(error: Throwable) {
    Log.e("Error", error.stackTraceToString())
}