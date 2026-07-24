package com.pathok.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseAuth
import com.pathok.app.auth.AuthScreen
import com.pathok.app.theme.PathokTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PathokTheme {
                var isLoggedIn by remember {
                    mutableStateOf(FirebaseAuth.getInstance().currentUser != null)
                }

                if (isLoggedIn) {
                    // এখনো Home স্ক্রিন বানানো হয়নি (Phase 2), আপাতত প্লেসহোল্ডার
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("✅ লগইন সফল! (Home স্ক্রিন পরের ধাপে আসবে)")
                    }
                } else {
                    AuthScreen(onAuthSuccess = { isLoggedIn = true })
                }
            }
        }
    }
}