package ru.carwash.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(

        @SerializedName("firstName")
        var firstName: String? = null,

        @SerializedName("lastName")
        var lastName: String? = null,

        @SerializedName("email")
        @Expose(serialize = true)
        var email: String? = null,

        @SerializedName("password")
        @Expose(serialize = true)
        var password: String? = null,

        @SerializedName("phone")
        var phone: String? = null,

        @SerializedName("city")
        var city: String? = null
) {
        constructor(email: String, password: String) : this() {

        }
}
