package com.example.bookshop.ui.main.wishlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshop.data.model.response.*
import com.example.bookshop.data.repository.wishlist.*
import com.example.bookshop.datasource.remote.RemoteDataSource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WishlistViewModel : ViewModel() {
    private val _wishList = MutableLiveData<WishlistResponse>()
    val wishList: LiveData<WishlistResponse> get() = _wishList
    private val _message = MutableLiveData<Message>()
    val message: LiveData<Message> get() = _message
    private val wishListRepository: WishListRepository = WishListRepositoryImp(RemoteDataSource())


    fun getWishList(limit: Int, page: Int, description_length: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = wishListRepository.getWishList(limit, page, description_length)
            if (response?.isSuccessful == true) {
                _wishList.postValue(response.body())
            } else {
                Log.d("GETWISHLIST", "NULL")
            }
        }
    }

}