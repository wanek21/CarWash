package ru.carwash.controllers

import android.content.Context
import android.content.SharedPreferences
import com.carwash.carwash.R

class SessionManager(private val context: Context) {

    companion object {
        const val USER_TOKEN = "user_token"
    }

    fun saveAuthToken(token: String) {
        DataProcessor.saveData(context, USER_TOKEN, token)
    }

    fun getAuthToken(): String? {
        return DataProcessor.getString(context, USER_TOKEN,"")
    }
}