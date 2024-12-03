package com.example.paymenttracking.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class, VisitorEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase :RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this){
                val tempInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                instance =tempInstance
                tempInstance
            }
        }
    }
}