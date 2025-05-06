package com.example.bookshop.ui.main.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshop.data.model.CartItem
import com.example.bookshop.data.repository.cart.CartRepository
import com.example.bookshop.data.repository.cart.CartRepositoryImp
import com.example.bookshop.data.model.response.ErrorResponse
import com.example.bookshop.data.model.response.Message
import com.example.bookshop.datasource.RemoteDataSource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val _cartItem = MutableLiveData<List<CartItem>>()
    val cartItem: LiveData<List<CartItem>> get() = _cartItem
    private val _message = MutableLiveData<Message>()
    val message: LiveData<Message> get() = _message
    private val _cartId = MutableLiveData<String>()
    val cartId: LiveData<String> get() = _cartId
    private val carRepository: CartRepository = CartRepositoryImp(RemoteDataSource())
    fun getAllCartItem() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = carRepository.getAllCart()
            if (response?.isSuccessful == true) {
                _cartItem.postValue(response.body()?.cartItem)
            } else {
                Log.d("GetAllCart", "NULL")
            }
        }
    }

    fun getCartId() {
        viewModelScope.launch(Dispatchers.IO) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = carRepository.getAllCart()
                if (response?.isSuccessful == true) {
                    _cartId.postValue(response.body()?.cartId)
                } else {
                    Log.d("GetAllCart", "NULL")
                }
            }
        }
    }

    fun deleteAllItemCart() {
        viewModelScope.launch(Dispatchers.IO) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = carRepository.deleteAllItemCart()
                if (response?.isSuccessful == true) {
                    _message.postValue(response.body())
                }
            }
        }
    }

    fun changeProductQuantityInCart(itemId: Int, quantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = carRepository.changeProductQuantityInCart(itemId, quantity)
            if (response?.isSuccessful == true) {

            } else {
                val errorBody = response?.errorBody()?.string()
                val gson = Gson()
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                _message.postValue(Message(errorResponse.error.message))
            }
        }
    }

    fun removeItemInCart(itemId: Int) {
        viewModelScope.launch(Dispatchers.IO){
            carRepository.removeItemInCart(itemId)
        }
    }
}