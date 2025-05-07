package com.example.bookshop.ui.category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshop.data.model.Category
import com.example.bookshop.data.repository.category.CategoryRepository
import com.example.bookshop.data.repository.category.CategoryRepositoryImp
import com.example.bookshop.datasource.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryIndexViewModel : ViewModel() {
    private val _categoryAllList = MutableLiveData<List<Category>>()
    val categoryAllList: LiveData<List<Category>> get() = _categoryAllList
    private var categotyRepository: CategoryRepository? = CategoryRepositoryImp(RemoteDataSource())
    fun getAllCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = categotyRepository?.getAllCategory()
            if (response?.isSuccessful == true) {
                _categoryAllList.postValue(response.body()?.categories)
            } else {
                Log.d("CategoryNULL", "NULL")
            }
        }
    }
}