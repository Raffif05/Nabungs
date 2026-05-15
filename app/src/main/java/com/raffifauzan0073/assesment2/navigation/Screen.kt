package com.raffifauzan0073.assesment2.navigation

const val KEY_ID_TRANSAKSI = "idTransaksi"

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_TRANSAKSI}") {
        fun withId(id: Long) =  "detailScreen/$id"
    }
}