package ru.carwash.controllers

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import ru.carwash.dto.LoginRequest
import ru.carwash.dto.LoginResponse
import ru.carwash.dto.User

interface ServerApi {

    @POST("/api/users/")
    fun registerUser(@Body user: User): Call<User>

    @POST("/api/users/login/")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/api/users/{phoneNumber}")
    fun getUser(@Path("phoneNumber") phoneNumber: String): Call<User>
}