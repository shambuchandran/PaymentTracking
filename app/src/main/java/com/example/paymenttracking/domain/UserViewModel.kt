package com.example.paymenttracking.domain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.paymenttracking.model.room.UserWithVisitors
import com.example.paymenttracking.model.room.VisitorEntity
import kotlinx.coroutines.launch

class UserViewModel(
    application: Application,
    private val repository: UserRepository
) :AndroidViewModel(application) {
    val userWithVisitors : LiveData<List<UserWithVisitors>> = repository.userWithVisitors

    fun fetchUsers()= viewModelScope.launch {
        repository.fetchUsers()
    }
    fun addVisitor(visitor: VisitorEntity) = viewModelScope.launch {
        repository.addVisitors(visitor)
    }

}

class UserViewModelFactory(
    private val application: Application,
    private val repository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}