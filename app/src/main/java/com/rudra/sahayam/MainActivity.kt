package com.rudra.sahayam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.rudra.sahayam.ui.screens.SahayamDashboardScreen
import com.rudra.sahayam.ui.theme.SahayamTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SahayamTheme {
                SahayamDashboardScreen()
            }
        }
    }
}
