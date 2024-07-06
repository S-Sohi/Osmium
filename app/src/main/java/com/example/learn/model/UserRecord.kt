package com.example.learn.model

import androidx.lifecycle.LiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.*

@Entity(tableName = "UserRecord",
    foreignKeys = [ForeignKey(
    entity = Cell::class,
    parentColumns = arrayOf("cid"),
    childColumns = arrayOf("cellId"),
    onDelete = ForeignKey.CASCADE
)])
data class UserRecord(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val lat: Double,
    val lng: Double,
    val power:Int,
    val cellId:Int
)


