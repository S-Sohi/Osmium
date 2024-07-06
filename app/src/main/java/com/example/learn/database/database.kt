package com.example.learn.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.learn.model.Cell
import com.example.learn.model.UserRecord

@Database(entities = [Cell::class], version = 1, exportSchema = false)
abstract class CellLocationDatabase: RoomDatabase(){

    abstract fun cellDao(): CellDao
    abstract fun userRecordDao(): UserRecordDao

    companion object {
        private var instance: CellLocationDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): CellLocationDatabase {
            if(instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, CellLocationDatabase::class.java,
                    "db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()

            return instance!!

        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        private fun populateDatabase(db: CellLocationDatabase) {
//            val noteDao = db.cellDao()
//            subscribeOnBackground {
//                noteDao.insert(Cell(1, 2))
//            }
        }

        private fun subscribeOnBackground(function: () -> Unit) {

        }
    }
}