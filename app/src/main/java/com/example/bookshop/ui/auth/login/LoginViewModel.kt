package com.example.bookshop.ui.auth.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshop.data.model.Customer
import com.example.bookshop.data.model.response.auth.AuthState
import com.example.bookshop.data.model.response.auth.AuthResponse
import com.example.bookshop.data.repository.user.UserRepository
import com.example.bookshop.data.repository.user.UserRepositoryImp
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.bookshop.data.model.response.Error
import com.example.bookshop.data.model.response.ErrorResponse
import com.example.bookshop.datasource.remote.RemoteDataSource

class LoginViewModel : ViewModel() {
    private var _loginResponse = MutableLiveData<AuthState>()
    val loginResponse: LiveData<AuthState> get() = _loginResponse
    private val _customer = MutableLiveData<Customer>()
    val customer: LiveData<Customer> get() = _customer
    private var userRepository: UserRepository? = UserRepositoryImp(RemoteDataSource())
    private val authRepository: UserRepository = UserRepositoryImp(RemoteDataSource())
    fun checkFields(user: AuthResponse) {

        if (user.isSignInFieldEmpty()) {
            _loginResponse.postValue(
                AuthState(
                    Error(message = "Vui lòng điền đầy đủ thông tin!"),
                    null
                )
            )
            return
        }

        if (!user.isValidEmail()) {
            _loginResponse.postValue(
                AuthState(
                    Error(message = "Email không hợp lệ!"),
                    null
                )
            )
            return
        }

        if (!user.isPasswordGreaterThan5(user.customer.password)) {
            _loginResponse.postValue(
                AuthState(
                    Error(
                        message =
                        "Mật khẩu phải dài hơn 5 kí tự bao gồm cả chữ và số"
                    ),
                    null
                )
            )
            return
        }
        checkLogin(user)

    }

    fun checkLogin(user: AuthResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = authRepository.login(user.customer.email, user.customer.password)
            if (response?.isSuccessful == true) {
                _loginResponse.postValue(
                    AuthState(
                        Error(message = "Đăng nhập thành công!"),
                        response.body()
                    )
                )
            } else {
                val errorBody = response?.errorBody()?.string()
                val gson = Gson()
                val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                _loginResponse.postValue(
                    AuthState(
                        Error(message = errorResponse.error.message),
                        null
                    )
                )
                Log.d("LoginNull", "NULL")
            }
        }
    }

    fun getCustomer() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = userRepository?.getCustomer()
            if (response?.isSuccessful == true) {
                _customer.postValue(response.body())
            } else {
                Log.d("getProfile", "NULLLL")
            }
        }
    }
}