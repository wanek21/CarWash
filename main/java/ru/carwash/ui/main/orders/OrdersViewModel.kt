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
import ru.carwash.dto.Review
import ru.carwash.utils.Resource
import ru.carwash.utils.SingleLiveEvent
import ru.carwash.utils.Status
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val ordersRepository: OrdersRepository) : ViewModel() {

    private val _orders: MutableLiveData<Resource<ArrayList<Order>>> = MutableLiveData()
    val orders: LiveData<Resource<ArrayList<Order>>> = _orders

    private val _sendingReviewStatus: MutableLiveData<Resource<String>> = MutableLiveData()
    val sendingReviewStatus: LiveData<Resource<String>> = _sendingReviewStatus

    // для правильной работы навигации
    private val _successSendingReview = SingleLiveEvent<Any>()
    val successSendingReview: LiveData<Any>
        get() = _successSendingReview

    init {
        _orders.value = Resource.loading(null)
        Log.d("my","OrdersViewModel init")
    }

    fun getOrders() {
        viewModelScope.launch {
            _orders.value = ordersRepository.getOrders()
        }
    }

    private fun successSendingReview() {
        _successSendingReview.call()
    }

    fun sendReview(review: Review) {
        viewModelScope.launch {
            _sendingReviewStatus.value = Resource.loading(null)
            _sendingReviewStatus.value = ordersRepository.sendReview(review)
            _sendingReviewStatus.value.also {
                if(it?.status == Status.SUCCESS)
                    successSendingReview()
            }
        }
    }
}