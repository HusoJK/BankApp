package com.olmez.bankapp.db

/**
 * Room DB`nin oluşturulduğu class
 */
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.olmez.bankapp.model.Branch

@Database(entities = arrayOf(Branch::class), version = 1)
abstract class BranchDatabase : RoomDatabase() {

    abstract fun branchDao() : BranchDao

    companion object {

       @Volatile private var instance : BranchDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,BranchDatabase::class.java,"branchdatabase"
        ).build()

    }

}