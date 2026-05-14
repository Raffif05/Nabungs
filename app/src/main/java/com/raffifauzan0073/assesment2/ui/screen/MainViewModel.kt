package com.raffifauzan0073.assesment2.ui.screen

import androidx.lifecycle.ViewModel
import com.raffifauzan0073.assesment2.model.Transaksi

class MainViewModel : ViewModel() {
    val data = listOf(
        Transaksi(
            1,
            "Beli nasi",
            15000,
            "2026-05-14"
        ),
        Transaksi(
            2,
            "Beli galon",
            18000,
            "2026-05-14"
        )
    )
}