package ru.carwash.screens.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.carwash.controllers.SessionManager
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    @Inject lateinit var sessionManager: SessionManager

    fun isLogged(): Boolean {
        return sessionManager.isLogged()
    }
}