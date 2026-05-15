package com.raffifauzan0073.assesment2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaksi")
data class Transaksi(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val nama: String,
    val nominal: Int,
    val tanggal: String,
    val kategori: String,
    val jenis: String
)
