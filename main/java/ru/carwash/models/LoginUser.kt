package ru.carwash.models

import com.google.gson.annotations.SerializedName

data class LoginUser(

        @SerializedName("email")
        var email: String,

        @SerializedName("password")
        var password: String,
)
