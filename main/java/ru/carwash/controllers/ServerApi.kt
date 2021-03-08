package ru.carwash.controllers

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ru.carwash.models.LoginResponse
import ru.carwash.models.LoginUser
import ru.carwash.models.User
import java.util.logging.LogRecord

interface ServerApi {

    @POST("/api/users/")
    fun registerUser(@Body user: User): Call<User>

    @POST("/api/users/login/")
    fun login(@Body loginUser: LoginUser): Call<LoginResponse>
}