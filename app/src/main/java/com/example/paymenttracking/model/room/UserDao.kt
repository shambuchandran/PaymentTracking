package com.example.paymenttracking.model.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Transaction
    @Query("SELECT * FROM users")
    fun getUsersWithVisitors():Flow<MutableList<UserWithVisitors>>

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserWithVisitors(userId: Int): Flow<UserWithVisitors>

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Query("UPDATE users SET paymentStatus = :newStatus WHERE id = :userId")
    suspend fun updatePaymentStatus(userId: Int, newStatus: Boolean)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVisitor(visitorEntity: VisitorEntity)

    @Query("DELETE FROM visitors WHERE userId = :userId")
    suspend fun deleteAllVisitorsOfUser(userId: Int?)

    @Delete
    suspend fun deleteVisitor(visitorEntity: VisitorEntity)
}