package com.example.learn.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.learn.model.Cell
import com.example.learn.model.UserRecord

@Dao
interface UserRecordDao {

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun insert(userRecord: UserRecord)
//
//    @Update
//    fun update(userRecord: UserRecord)
//
//    @Delete
//    fun delete(userRecord: UserRecord)
//
//    @Query("delete from UserRecord")
//    fun deleteAllNotes()
//
//    @Query("select * from UserRecord")
//    fun getAllNotes(): LiveData<List<UserRecord>>
}