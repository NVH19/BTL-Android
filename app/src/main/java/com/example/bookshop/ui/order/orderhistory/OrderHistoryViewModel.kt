package com.example.bookshop.ui.order.orderhistory

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshop.data.model.Order
import com.example.bookshop.data.repository.order.OrderRepository
import com.example.bookshop.data.repository.order.OrderRepositoryImp
import com.example.bookshop.datasource.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderHistoryViewModel : ViewModel() {
    private val _orderHistory = MutableLiveData<List<Order>>()
    val orderHistory: MutableLiveData<List<Order>> get() = _orderHistory
    private var orderHistoryRepository: OrderRepository = OrderRepositoryImp(RemoteDataSource())
    fun getOrderHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = orderHistoryRepository.getOrderHistory()
            if (response?.isSuccessful == true) {
                _orderHistory.postValue(response.body()?.orders)
            } else {
                Log.d("GetOrderHistory", "NULLL")
            }
        }
    }
}