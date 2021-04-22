package ru.carwash.ui.main.orders

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.carwash.data.repositories.OrdersRepository
import ru.carwash.dto.Order
import ru.carwash.utils.Resource
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val ordersRepository: OrdersRepository) : ViewModel() {

    private val _orders: MutableLiveData<Resource<ArrayList<Order>>> = MutableLiveData()
    val orders: LiveData<Resource<ArrayList<Order>>> = _orders



    init {
        _orders.value = Resource.loading(null)
        Log.d("my","OrdersViewModel init")
    }

    fun getOrders() {
        viewModelScope.launch {
            _orders.value = ordersRepository.getOrders()
        }
    }
}