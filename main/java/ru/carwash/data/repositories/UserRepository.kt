package ru.carwash.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.carwash.controllers.SessionManager
import ru.carwash.data.remote.WebService
import ru.carwash.dto.*
import ru.carwash.utils.Resource
import ru.carwash.utils.Status
import javax.inject.Inject

class UserRepository @Inject constructor(
        private val webService: WebService,
        private val sessionManager: SessionManager) {

    fun login(loginRequest: LoginRequest) : Resource<String> {
        val resource = webService.login(loginRequest)
        if(resource.status == Status.SUCCESS) {
            resource.data?.let { sessionManager.login(it) } // фиксируем, что юзер авторизовался и входить больше не нужно
        }
        return resource
    }

    fun register(user: User): Resource<String> {
        return webService.register(user)
    }

    /*suspend fun getCars() : Resource<ArrayList<Car>> {
        return withContext(Dispatchers.IO) {
            delay(2000L)
            webService.getCars()
        }
    }

    suspend fun addCar(car: AddEditCar): Resource<String> {
        return withContext(Dispatchers.IO) {
            delay(500L)
            webService.addCar(car)
        }
    }

    suspend fun editCar(car: AddEditCar): Resource<String> {
        return withContext(Dispatchers.IO) {
            webService.editCar(car)
        }
    }*/

}