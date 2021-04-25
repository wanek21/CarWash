package ru.carwash.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.carwash.data.remote.WebService
import ru.carwash.dto.CarWash
import ru.carwash.dto.Order
import ru.carwash.dto.Review
import ru.carwash.dto.Service
import ru.carwash.utils.Resource
import javax.inject.Inject

class OrdersRepository @Inject constructor(
        private val webService: WebService
){

    suspend fun createOrder(order: Order): Resource<String> {
        return withContext(Dispatchers.IO) {
            delay(1200)
            webService.createOrder(order)
        }
    }
    suspend fun getOrders(): Resource<ArrayList<Order>> {
        return withContext(Dispatchers.IO) {
            delay(500)
            webService.getOrders()
        }
    }
    suspend fun sendReview(review: Review): Resource<String> {
        return withContext(Dispatchers.IO) {
            delay(500)
            webService.sendReview(review)
        }
    }
    suspend fun getAvailableCarWashes(): Resource<ArrayList<CarWash>> {
        return withContext(Dispatchers.IO) {
            delay(700)
            webService.getAvailableCarWashes()
        }
    }
    suspend fun getAvailableServices(): Resource<ArrayList<Service>> {
        return withContext(Dispatchers.IO) {
            delay(500)
            webService.getAvailableServices()
        }
    }
}