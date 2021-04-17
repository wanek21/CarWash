package ru.carwash.controllers

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManager @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        const val USER_TOKEN = "user_token"
        const val IS_LOGGED = "is_logged"
    }

    fun login(token: String) {
        saveAuthToken(token)
        DataProcessor.saveData(context, IS_LOGGED, true)
    }
    fun logout() {
        DataProcessor.saveData(context, IS_LOGGED, false)
    }
    fun isLogged(): Boolean {
        return DataProcessor.getBoolean(context, IS_LOGGED,false)
    }
    private fun saveAuthToken(token: String) {
        DataProcessor.saveData(context, USER_TOKEN, token)
    }

    fun getAuthToken(): String? {
        return DataProcessor.getString(context, USER_TOKEN,"")
    }
}