package ru.carwash.screens.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.carwash.data.repositories.UserRepository
import ru.carwash.dto.LoginRequest
import ru.carwash.utils.Resource
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor(
       private val userRepository: UserRepository)
    : ViewModel() {

    val loginStatus: MutableLiveData<Resource<String>> by lazy {
        MutableLiveData<Resource<String>>()
    }
    private lateinit var loginErrorMsg: String

    fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        if(isEmailValid(email) && isPasswordValid(password))
            loginStatus.value = userRepository.login(loginRequest)
        else loginStatus.value = Resource.error(loginErrorMsg,null)
    }

    private fun isEmailValid(email: String): Boolean {
        return email.validator()
                .validEmail()
                .addErrorCallback {
                    loginErrorMsg = "Некорректный email"
                }
                .check()
    }
    private fun isPasswordValid(password: String): Boolean {
        return password.validator()
                .nonEmpty()
                .minLength(6)
                .addErrorCallback {
                    loginErrorMsg = "Пароль должен быть не меньше 6 символов"
                }
                .check()
    }
}