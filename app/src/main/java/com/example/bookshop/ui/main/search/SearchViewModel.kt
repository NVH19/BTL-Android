package com.example.bookshop.ui.main.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshop.data.model.Product
import com.example.bookshop.data.model.reponse.product.ProductState
import com.example.bookshop.data.repository.search.SearchRepository
import com.example.bookshop.data.repository.search.SearchRepositoryImp
import com.example.bookshop.datasource.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : ViewModel() {
    private val _productState = MutableLiveData<ProductState>()
    val productState: LiveData<ProductState> get() = _productState

    private val _productNameList = MutableLiveData<List<Product>>()
    val productNameList: LiveData<List<Product>> get() = _productNameList

    private var searchRepository: SearchRepository? = SearchRepositoryImp(RemoteDataSource())


    fun getSearchProducts(
        limit: Int,
        page: Int,
        description_length: Int,
        query_string: String,
        filter_type: Int,
        price_sort_order: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = searchRepository?.getSearchProducts(
                limit,
                page,
                description_length,
                query_string,
                filter_type,
                price_sort_order
            )
            if (response?.isSuccessful == true) {
                if (query_string.isEmpty()) {
                    _productState.postValue(ProductState(response.body()?.products, true))
                } else {
                    _productState.postValue(ProductState(response.body()?.products, false))
                }
            } else {
                Log.d("SearchProduct", "NULLLL")
            }
        }
    }
}