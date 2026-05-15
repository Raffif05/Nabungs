package com.raffifauzan0073.assesment2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.raffifauzan0073.assesment2.model.Transaksi
import kotlinx.coroutines.flow.Flow

@Dao
interface TransaksiDao {
    @Insert
    suspend fun insert(transaksi: Transaksi)

    @Update
    suspend fun update(transaksi: Transaksi)

    @Query("SELECT * FROM transaksi ORDER BY tanggal DESC")
    fun getTransaksi(): Flow<List<Transaksi>>
}