package com.rudra.sahayam.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rudra.sahayam.ui.navigation.Routes
import com.rudra.sahayam.viewmodel.AuthViewModel

@Composable
fun WelcomeScreen(navController: NavController, viewModel: AuthViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sahayam", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { navController.navigate(Routes.LOGIN) }) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate(Routes.SIGNUP) }) {
            Text("Sign Up")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { 
            viewModel.startGuestSession()
            navController.navigate(Routes.HOME) {
                popUpTo(Routes.WELCOME) { inclusive = true }
            }
        }) {
            Text("Continue as Guest")
        }
    }
}
