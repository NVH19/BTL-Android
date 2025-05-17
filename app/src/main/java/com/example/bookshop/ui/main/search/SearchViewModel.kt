package com.example.bookshop.ui.main.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshop.data.model.Product
import com.example.bookshop.data.model.response.product.ProductState
import com.example.bookshop.data.repository.search.historysearch.HistorySearchRepository
import com.example.bookshop.data.repository.search.SearchRepository
import com.example.bookshop.data.repository.search.SearchRepositoryImp
import com.example.bookshop.data.repository.search.historysearch.HistorySearchRepositoryImp
import com.example.bookshop.datasource.remote.RemoteDataSource
import com.example.bookshop.datasource.local.db.entity.ProductDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : ViewModel() {
    private val _productState = MutableLiveData<ProductState>()
    val productState: LiveData<ProductState> get() = _productState
    private val _productNameList = MutableLiveData<List<Product>>()
    val productNameList: LiveData<List<Product>> get() = _productNameList
    private var searchRepository: SearchRepository? = SearchRepositoryImp(RemoteDataSource())
    private val _historyList = MutableLiveData<List<String>>()
    val historyList: LiveData<List<String>> get() = _historyList
    var job: Job? = null
    private val historySearchRepository: HistorySearchRepository =
        HistorySearchRepositoryImp(application)

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

    fun getHistorySearchLocal(idCustomer: Int) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            val response = historySearchRepository.getAllProducts(idCustomer)
            _historyList.postValue(response)
        }
    }

    fun getProductSuggestions(queryString: String) {
        job?.cancel()
        job=viewModelScope.launch(Dispatchers.IO) {
            val response = searchRepository?.getProductSuggestions(queryString)
            if (response?.isSuccessful == true) {
                _productNameList.postValue(response.body()?.products)
            } else {
                Log.d("SearchHistory", "NULL")
            }
        }
    }

    fun insertHistorySearchLocal(product: ProductDb) {
        viewModelScope.launch(Dispatchers.IO) {
            historySearchRepository.insertProduct(product)
        }
    }

    fun deleteHistorySearchLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            historySearchRepository.deleteAllProducts()
        }
    }

    fun removeItemHistorySearchLocal(productName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            historySearchRepository.deleteColName(productName)
        }
    }
}