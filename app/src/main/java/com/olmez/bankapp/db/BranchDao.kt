package com.olmez.bankapp.db

/**
 *  Detay ekranında gösterilecek şubenin bilgilerini tutmak için oluşturulan Room DB`nin DAO Katmanı
 */

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.olmez.bankapp.model.Branch

@Dao
interface BranchDao {

    @Insert
    suspend fun insertBranches(vararg branches: Branch): List<Long>

    @Query("SELECT * FROM branch")
    suspend fun getAllBranches(): List<Branch>

    @Query("SELECT * FROM branch WHERE id = :branchID")
    suspend fun getBranch(branchID: Int): Branch

    @Query("DELETE FROM branch")
    suspend fun deleteAllBranches()


}