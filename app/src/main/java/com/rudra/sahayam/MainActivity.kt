package com.sahayam.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.rudra.sahayam.ui.navigation.NavGraph
import com.sahayam.app.ui.theme.SahayamTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SahayamTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}
