package com.example.learn.model

import androidx.lifecycle.LiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.*

@Entity(tableName = "Cell")
data class Cell(
    @PrimaryKey(autoGenerate = false) val cid: Int? = null,
    val gen: Int
)


