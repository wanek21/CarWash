package ru.carwash.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.carwash.data.remote.WebService
import ru.carwash.dto.AddEditCar
import ru.carwash.dto.Car
import ru.carwash.utils.Resource
import javax.inject.Inject

class CarsRepository @Inject constructor(
        private val webService: WebService
){
    suspend fun getCars() : Resource<ArrayList<Car>> {
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
    }
}