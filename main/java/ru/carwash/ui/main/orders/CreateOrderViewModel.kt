package ru.carwash.ui.main.orders

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.carwash.data.repositories.CarsRepository
import ru.carwash.data.repositories.OrdersRepository
import ru.carwash.dto.Car
import ru.carwash.dto.CarWash
import ru.carwash.dto.Order
import ru.carwash.dto.Service
import ru.carwash.utils.Resource
import ru.carwash.utils.SingleLiveEvent
import ru.carwash.utils.Status
import javax.inject.Inject

@HiltViewModel
class CreateOrderViewModel @Inject constructor(
        private val ordersRepository: OrdersRepository,
        private val carsRepository: CarsRepository
) : ViewModel() {

    // для проверки корректности заполненных данных заказа
    private val _orderValidity: MutableLiveData<Resource<String>> = MutableLiveData()
    val orderValidity: LiveData<Resource<String>> = _orderValidity

    // для проверки, что запрос на создание заказа на сервере успешно прошел
    private val _creatingOrderComplete: MutableLiveData<Resource<String>> = MutableLiveData()
    val creatingOrderComplete: LiveData<Resource<String>> = _creatingOrderComplete

    // для навигации
    private val _successCreatingOrder = SingleLiveEvent<Any>()
    val successCreatingOrder: LiveData<Any>
        get() = _successCreatingOrder

    // для навигации
    private val _successValidatingOrder = SingleLiveEvent<Any>()
    val successValidatingOrder: LiveData<Any>
        get() = _successValidatingOrder

    private val _carWashes: MutableLiveData<Resource<ArrayList<CarWash>>> = MutableLiveData()
    val carWashes: LiveData<Resource<ArrayList<CarWash>>> = _carWashes

    private val _clientsCars: MutableLiveData<Resource<ArrayList<Car>>> = MutableLiveData()
    val clientsCars: LiveData<Resource<ArrayList<Car>>> = _clientsCars

    private val _services: MutableLiveData<Resource<ArrayList<Service>>> = MutableLiveData()
    val services: LiveData<Resource<ArrayList<Service>>> = _services

    //val order: MutableLiveData<Order> = MutableLiveData()

    init {
        //order.value = Order(Order.ACCEPTED_STATUS)
        getAvailableCarWashes()
        getClientsCars()
        getAvailableServices()
        Log.d("my","creating Order ViewModel init")
    }

    private fun successValidatingOrder() {
        _successValidatingOrder.call()
    }

    private fun successCreatingOrder() {
        _successCreatingOrder.call()
    }

    fun createOrder(order: Order?) {
        viewModelScope.launch {
            if (orderValidity.value?.status == Status.SUCCESS && order != null) { // если заполненные данные заказа корректные
                _creatingOrderComplete.value = Resource.loading(null)
                _creatingOrderComplete.value = ordersRepository.createOrder(order)

                _creatingOrderComplete.also {
                    if(it.value?.status == Status.SUCCESS)
                        successCreatingOrder()
                }
            }
        }
    }

    fun getAvailableCarWashes() {
        viewModelScope.launch {
            _carWashes.value = Resource.loading(null)
            _carWashes.value = ordersRepository.getAvailableCarWashes()
        }
    }

    fun getClientsCars() {
        viewModelScope.launch {
            if (_clientsCars.value == null)
                _clientsCars.value = Resource.loading(null)
            _clientsCars.value = carsRepository.getCars()
        }
    }

    fun getAvailableServices() {
        viewModelScope.launch {
            _services.value = Resource.loading(null)
            _services.value = ordersRepository.getAvailableServices()
        }
    }

    /*fun setCarWash(carWash: CarWash) {
        order.value?.carWash = carWash
    }

    fun setCar(car: Car) {
        order.value?.car = car
    }

    fun setDate(day: Int, month: Int, year: Int) {
        order.value?.date = "$day.$month.$year"
    }

    fun setTime(hour: Int, minute: Int) {
        order.value?.time = "${hour}:$minute"
    }

    fun setServices(services: ArrayList<Service>) {
        order.value?.services = services
    }*/

    fun checkOrderValidity(order: Order) {
        _orderValidity.value = Resource.success(null)

        if(order.carWash == null) {
            _orderValidity.value = Resource.error("Вы не выбрали автомойку", null)
            return
        }
        if(order.car == null) {
            _orderValidity.value = Resource.error("Вы не выбрали авотмобиль", null)
            return
        }
        if(order.date == null) {
            _orderValidity.value = Resource.error("Вы не выбрали дату", null)
            return
        }
        if(order.time == null) {
            _orderValidity.value = Resource.error("Вы не выбрали время", null)
            return
        }
        if(order.services == null || order.services!!.size == 0) {
            _orderValidity.value = Resource.error("Выберите хотя бы одну услугу", null)
            return
        }


        _orderValidity.also {
            if(it.value?.status == Status.SUCCESS)
                successValidatingOrder()
        }
    }
}