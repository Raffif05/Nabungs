package com.raffifauzan0073.assesment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raffifauzan0073.assesment2.database.TransaksiDao
import com.raffifauzan0073.assesment2.model.Transaksi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: TransaksiDao) : ViewModel() {
    val data: StateFlow<List<Transaksi>> = dao.getTransaksi().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun getTransaksi(id: Long): Transaksi? {
        return data.value.find { it.id == id }
    }
}