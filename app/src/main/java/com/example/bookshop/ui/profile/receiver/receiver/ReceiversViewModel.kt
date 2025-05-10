package com.example.BookShopApp.ui.profile.receiver.receiver

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshop.data.model.Receiver
import com.example.bookshop.data.model.response.Message
import com.example.bookshop.data.model.response.ReceiverResponse
import com.example.bookshop.data.repository.receiver.ReceiverRepository
import com.example.bookshop.data.repository.receiver.ReceiverRepositoryImp
import com.example.bookshop.data.repository.user.UserRepository
import com.example.bookshop.data.repository.user.UserRepositoryImp
import com.example.bookshop.datasource.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReceiversViewModel : ViewModel() {
    private val _receivers = MutableLiveData<List<Receiver>>()
    val receivers: LiveData<List<Receiver>> get() = _receivers
    private val _message = MutableLiveData<Message>()
    val message: LiveData<Message> get() = _message
    private var receiverRepository: ReceiverRepository? = ReceiverRepositoryImp(RemoteDataSource())
    fun getReceivers() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = receiverRepository?.getReceivers()
            if (response?.isSuccessful == true) {
                _receivers.postValue(response.body()?.receivers)
            } else {
                Log.d("GetReceivers", "NULL")
            }
        }
    }

    fun removeReceiver(receiverId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = receiverRepository?.removeReceiver(receiverId)
            if (response?.isSuccessful == true) {
                _message.postValue(response.body())
            } else {
                Log.d("removeReceivers", "NULL")
            }
        }
    }
    fun clearMessage(){
        _message.postValue(Message(""))
    }
}