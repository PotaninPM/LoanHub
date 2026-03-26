package com.potaninpm.database.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.potaninpm.database.data.room.entity.LoanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoanDao {
    @Query("SELECT * FROM loan_drafts")
    fun getAll(): Flow<List<LoanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(loan: LoanEntity)

    @Query("DELETE FROM loan_drafts WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM loan_drafts WHERE id = :id")
    suspend fun getById(id: String): LoanEntity?
}
