package com.pathok.app.auth

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.pathok.app.R
import com.pathok.app.theme.LocalPathokColors
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip

@Composable
fun AuthScreen(onAuthSuccess: () -> Unit) {
    val colors = LocalPathokColors.current
    val context = LocalContext.current
    val auth = remember { FirebaseAuth.getInstance() }

    var isSignupMode by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val googleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        try {
            val account = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                .getResult(ApiException::class.java)
            val idToken = account?.idToken
            if (idToken != null) {
                isLoading = true
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    isLoading = false
                    if (task.isSuccessful) onAuthSuccess()
                    else errorMsg = "Google লগইন ব্যর্থ হয়েছে।"
                }
            }
        } catch (e: ApiException) {
            errorMsg = "লগইন বাতিল করা হয়েছে।"
        }
    }

    fun startGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val client = GoogleSignIn.getClient(context as Activity, gso)
        googleLauncher.launch(client.signInIntent)
    }

    fun handleEmailAuth() {
        errorMsg = null
        if (email.isBlank() || password.isBlank()) {
            errorMsg = "ইমেইল ও পাসওয়ার্ড দিন।"
            return
        }
        if (password.length < 6) {
            errorMsg = "পাসওয়ার্ড কমপক্ষে ৬ অক্ষর হতে হবে।"
            return
        }
        isLoading = true
        if (isSignupMode) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) onAuthSuccess()
                else errorMsg = task.exception?.localizedMessage ?: "সাইন আপ ব্যর্থ হয়েছে।"
            }
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) onAuthSuccess()
                else errorMsg = task.exception?.localizedMessage ?: "লগইন ব্যর্থ হয়েছে।"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.bg)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // লোগো
        Text("📚", fontSize = 48.sp)
        Text("পাঠক", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = colors.accent)
        Text("বাংলা ডিজিটাল পাঠাগার", fontSize = 13.sp, color = colors.text2)

        Spacer(Modifier.height(28.dp))

        // Auth কার্ড
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.cardBg, RoundedCornerShape(18.dp))
                .border(1.dp, colors.surfaceBorder, RoundedCornerShape(18.dp))
                .padding(20.dp)
        ) {
            // ট্যাব রো
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.bg2, RoundedCornerShape(10.dp))
                    .padding(4.dp)
            ) {
                AuthTabButton("সাইন আপ", isSignupMode, colors.accent, colors.cardBg, colors.text2) {
                    isSignupMode = true; errorMsg = null
                }
                Spacer(Modifier.width(4.dp))
                AuthTabButton("লগইন", !isSignupMode, colors.accent, colors.cardBg, colors.text2) {
                    isSignupMode = false; errorMsg = null
                }
            }

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("ইমেইল ঠিকানা") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("পাসওয়ার্ড (কমপক্ষে ৬ অক্ষর)") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            errorMsg?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = Color(0xFFD32F2F), fontSize = 13.sp)
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { handleEmailAuth() },
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
                } else {
                    Text(if (isSignupMode) "অ্যাকাউন্ট তৈরি করুন" else "লগইন করুন", color = Color.White)
                }
            }

            Spacer(Modifier.height(14.dp))
            Text("— অথবা —", modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = colors.muted, fontSize = 13.sp)
            Spacer(Modifier.height(10.dp))

            OutlinedButton(
                onClick = { startGoogleSignIn() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("G", fontWeight = FontWeight.Bold, color = Color(0xFF4285F4))
                Spacer(Modifier.width(8.dp))
                Text("Google দিয়ে প্রবেশ করুন", color = Color(0xFF333333))
            }
        }
    }
}

@Composable
private fun RowScope.AuthTabButton(
    text: String,
    active: Boolean,
    activeColor: Color,
    activeBg: Color,
    inactiveColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(if (active) activeBg else Color.Transparent)
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            color = if (active) activeColor else inactiveColor,
            fontWeight = if (active) FontWeight.Bold else FontWeight.Normal
        )
    }
}