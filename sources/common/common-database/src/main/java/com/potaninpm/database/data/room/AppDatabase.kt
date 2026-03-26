package com.potaninpm.database.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.potaninpm.database.data.room.dao.LoanDao
import com.potaninpm.database.data.room.entity.LoanEntity

@Database(entities = [LoanEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun loanDao(): LoanDao
}
