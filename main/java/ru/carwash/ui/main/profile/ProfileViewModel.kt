package ru.carwash.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.carwash.data.repositories.UserRepository
import ru.carwash.dto.User
import ru.carwash.utils.Resource
import ru.carwash.utils.SingleLiveEvent
import ru.carwash.utils.Status
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
        private val userRepository: UserRepository): ViewModel() {

    private val _userInfo: MutableLiveData<Resource<User>> = MutableLiveData()
    val userInfo: LiveData<Resource<User>> = _userInfo

    private val _editingUserStatus: MutableLiveData<Resource<String>> = MutableLiveData()
    val editingUserStatus: LiveData<Resource<String>> = _editingUserStatus

    // для правильной работы навигации
    private val _successEditingUser = SingleLiveEvent<Any>()
    val successEditingUser: LiveData<Any>
        get() = _successEditingUser

    init {
        getUserInfo()
    }

    fun getUserInfo() {
        viewModelScope.launch {
            _userInfo.value = Resource.loading(null)
            _userInfo.value = userRepository.getUserInfo()
        }
    }

    private fun successEditingUser() {
        _successEditingUser.call()
    }

    fun editUser(user: User) {
        viewModelScope.launch {
            _editingUserStatus.value = Resource.loading(null)
            _editingUserStatus.value = userRepository.editUser(user)

            _editingUserStatus.value.also {
                if(it?.status == Status.SUCCESS)
                    successEditingUser()
            }
        }
    }

    fun logout() {
        userRepository.logout()
    }
}