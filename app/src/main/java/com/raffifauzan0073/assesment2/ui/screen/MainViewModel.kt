package com.raffifauzan0073.assesment2.ui.screen

import androidx.lifecycle.ViewModel
import com.raffifauzan0073.assesment2.model.Transaksi

class MainViewModel : ViewModel() {
    val data = listOf(
        Transaksi(
            1,
            "Beli nasi",
            15000,
            "2026-05-15",
            "Makan",
            "Pengeluaran"
        ),
        Transaksi(
            2,
            "Beli galon",
            13000,
            "2026-05-15",
            "Makan",
            "Pengeluaran"
        ),
        Transaksi(
            3,
            "Beli internet",
            20000,
            "2026-05-13",
            "Tagihan",
            "Pengeluaran"
        ),
        Transaksi(
            4,
            "Saldo Awal",
            1000000,
            "2026-05-15",
            "",
            "Pemasukan"
        )
    )
    fun getTransaksi(id: Long): Transaksi? {
        return data.find { it.id == id }
    }
}