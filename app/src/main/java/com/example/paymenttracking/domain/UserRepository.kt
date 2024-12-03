package com.example.paymenttracking.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.paymenttracking.model.apiresponse.ApiService
import com.example.paymenttracking.model.room.UserDao
import com.example.paymenttracking.model.room.UserEntity
import com.example.paymenttracking.model.room.UserWithVisitors
import com.example.paymenttracking.model.room.VisitorEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val apiService: ApiService,
    private val userDao: UserDao
) {
    val userWithVisitors: LiveData<List<UserWithVisitors>> =
        userDao.getUsersWithVisitors().asLiveData()

    suspend fun fetchUsers() {
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getUsers()
                if (response.isSuccessful) {
                    Log.d("UserRepository", "Fetched Users from API: ${response.body()?.results}")
                    response.body()?.results?.forEach {
                        val user = UserEntity(
                            id=0,
                            name = "${it.name.first} ${it.name.last}",
                            profilePictureUrl = it.picture.large
                        )
                        userDao.insertUser(user)
                        Log.d("UserRepository", "Inserted User: $user")
                    }
                } else {
                    Log.e("UserRepository", "API Error: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("UserRepository", "Network Error: ${e.message}")
            }
        }
    }

    suspend fun addVisitors(visitor: VisitorEntity) {
        withContext(Dispatchers.IO) {
            userDao.insertVisitor(visitor)
        }
    }

}