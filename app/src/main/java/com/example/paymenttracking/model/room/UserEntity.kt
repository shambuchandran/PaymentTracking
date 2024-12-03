package com.example.paymenttracking.model.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val profilePictureUrl: String,
    var paymentStatus: Boolean = false,
    var paymentAmount: Int = 2500
)

@Entity(
    tableName = "visitors",
    foreignKeys = [ForeignKey(entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )])
data class VisitorEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val name: String,
    val paymentAmount: Int = 1000
)
data class UserWithVisitors(
    @Embedded
    val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val visitors: MutableList<VisitorEntity>
)