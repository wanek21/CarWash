package ru.carwash.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
        @SerializedName("access_token")
        var access_token: String
)
