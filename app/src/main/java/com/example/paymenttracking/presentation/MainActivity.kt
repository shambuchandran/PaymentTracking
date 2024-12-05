package com.example.paymenttracking.presentation

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paymenttracking.R
import com.example.paymenttracking.domain.UserAdaptor
import com.example.paymenttracking.domain.UserRepository
import com.example.paymenttracking.domain.UserViewModel
import com.example.paymenttracking.domain.UserViewModelFactory
import com.example.paymenttracking.model.apiresponse.RetroFitInstance
import com.example.paymenttracking.model.room.AppDatabase
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdaptor: UserAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val toolbar:MaterialToolbar = findViewById(R.id.materialToolbar)
        setSupportActionBar(toolbar)

        val userDao = AppDatabase.getDatabase(application).userDao()
        val repository = UserRepository(RetroFitInstance.api, userDao)
        val factory = UserViewModelFactory(application,repository)
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]
        userAdaptor = UserAdaptor()
        val recyclerView:RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = userAdaptor
        recyclerView.layoutManager =LinearLayoutManager(this)

        userViewModel.fetchUsers()
        userViewModel.userWithVisitors.observe(this){
            userAdaptor.setData(it)
            Log.d("User",it.toString())
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_delete ->{
                confirmDeleteAllUsers()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun confirmDeleteAllUsers(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete All Users")
        builder.setMessage("Are you sure you want to delete all users?")
        builder.setPositiveButton("Delete"){dialog,_ ->
            deleteAllUsers()
        }
        builder.setNegativeButton("cancel"){dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }
    private fun deleteAllUsers() {
        userViewModel.deleteAllUsers()
        Toast.makeText(this, "All users deleted", Toast.LENGTH_SHORT).show()
    }
}