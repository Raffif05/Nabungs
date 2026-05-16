package com.raffifauzan0073.assesment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raffifauzan0073.assesment2.database.TransaksiDao
import com.raffifauzan0073.assesment2.model.Transaksi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(private val dao: TransaksiDao) : ViewModel() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun insert(nama: String, nominal: Int, kategori: String, jenis: String) {
        val transaksi = Transaksi(
            tanggal = formatter.format(Date()),
            nama = nama,
            nominal = nominal,
            kategori = kategori,
            jenis = jenis
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(transaksi)
        }
    }

    suspend fun getTransaksi(id: Long): Transaksi? {
        return dao.getTransaksiById(id)
    }

    fun update(id: Long, nama: String, nominal: Int, kategori: String, jenis: String) {
        val transaksi = Transaksi(
            id = id,
            tanggal = formatter.format(Date()),
            nama = nama,
            nominal = nominal,
            kategori = kategori,
            jenis = jenis
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(transaksi)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}