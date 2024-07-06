package com.example.learn.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.learn.model.Cell

@Dao
interface CellDao {

    @Insert
    fun insert(cell: Cell)

    @Update
    fun update(cell: Cell)

    @Delete
    fun delete(cell: Cell)

    @Query("delete from cell")
    fun deleteAllNotes()

    @Query("select * from Cell")
    fun getAllCells(): LiveData<List<Cell>>
}