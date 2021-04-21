package ru.carwash.ui.auth

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carwash.carwash.R
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.carwash.data.repositories.UserRepository
import ru.carwash.dto.User
import ru.carwash.utils.Resource
import ru.carwash.utils.validation.MinLengthRule
import ru.carwash.utils.validation.NonEmptyRule
import ru.carwash.utils.validation.ValidNumberRule
import javax.inject.Inject

@HiltViewModel
class SignUpActivityViewModel @Inject constructor(
        private val userRepository: UserRepository, application: Application) : AndroidViewModel(application) {

    val regStatus: MutableLiveData<Resource<String>> by lazy {
        MutableLiveData<Resource<String>>()
    }

    private lateinit var regErrorMessage: String
    private val context = getApplication<Application>()

    fun register(user: User) {
        if (isNameValid(user.firstName) &&
                isNameValid(user.lastName) &&
                isCityValid(user.city) &&
                isPhoneNumberValid(user.phone) &&
                isEmailValid(user.email) &&
                isPasswordValid(user.password)) {
            regStatus.value = userRepository.register(user)
        } else regStatus.value = Resource.error(regErrorMessage,null)

    }

    private fun isNameValid(name: String?): Boolean {
        return name?.validator()
                ?.addRule(NonEmptyRule(context.getString(R.string.name_is_empty)))
                ?.addRule(MinLengthRule(context.getString(R.string.name_min_length), 2))
                ?.addErrorCallback {
                    regErrorMessage = it
                }?.check() == true
    }

    private fun isCityValid(city: String?): Boolean {
        return city?.validator()
                ?.addRule(NonEmptyRule(context.getString(R.string.city_is_empty)))
                ?.addErrorCallback {
                    regErrorMessage = it
                }?.check() == true
    }

    private fun isPhoneNumberValid(number: String?): Boolean {
        return number?.validator()
                ?.addRule(NonEmptyRule(context.getString(R.string.phone_is_empty)))
                ?.addRule(ValidNumberRule(context.getString(R.string.invalid_phone_number)))
                ?.addErrorCallback {
                    regErrorMessage = it
                }?.check() == true
    }

    private fun isEmailValid(email: String?): Boolean {
        return email?.validator()
                ?.validEmail()
                ?.addErrorCallback {
                    regErrorMessage = it
                }
                ?.check() == true
    }

    private fun isPasswordValid(pass: String?): Boolean {
        return pass?.validator()
                ?.addRule(NonEmptyRule(context.getString(R.string.pass_is_empty)))
                ?.addRule(MinLengthRule(context.getString(R.string.pass_min_length), 8))
                ?.addErrorCallback {
                    regErrorMessage = it
                }
                ?.check() == true
    }
}