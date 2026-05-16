package com.raffifauzan0073.assesment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.raffifauzan0073.assesment2.navigation.SetupNavGraph
import com.raffifauzan0073.assesment2.ui.theme.Assesment2Theme
import com.raffifauzan0073.assesment2.util.SettingsDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStore = SettingsDataStore(this)
        setContent {
            val isDarkMode by dataStore.themeFlow.collectAsState(false)
            Assesment2Theme(darkTheme = isDarkMode) {
                SetupNavGraph()
            }
        }
    }
}
