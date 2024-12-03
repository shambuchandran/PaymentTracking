package com.example.paymenttracking.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.paymenttracking.R
import com.example.paymenttracking.domain.UserRepository
import com.example.paymenttracking.domain.UserViewModel
import com.example.paymenttracking.domain.UserViewModelFactory
import com.example.paymenttracking.model.apiresponse.RetroFitInstance
import com.example.paymenttracking.model.room.AppDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val userDao = AppDatabase.getDatabase(application).userDao()
        val repository = UserRepository(RetroFitInstance.api, userDao)
        val factory = UserViewModelFactory(application,repository)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        userViewModel.fetchUsers()

        userViewModel.userWithVisitors.observe(this){
            Log.d("User",it.toString())
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}