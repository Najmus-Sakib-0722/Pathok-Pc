package com.pathok.app

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var offlineBar: LinearLayout

    private val RC_SIGN_IN = 9001
    private val FILE_CHOOSER_RESULT_CODE = 2

    private var filePathCallback: ValueCallback<Array<Uri>>? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)
        offlineBar = findViewById(R.id.offlineBar)

        setupWebView()
        webView.loadUrl("file:///android_asset/index.html")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        webView.overScrollMode = View.OVER_SCROLL_NEVER
        webView.isHorizontalScrollBarEnabled = false
        webView.isVerticalScrollBarEnabled = false
        webView.setOnLongClickListener { true }
        webView.isLongClickable = false

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            allowFileAccess = true
            allowContentAccess = true
            databaseEnabled = true
            allowFileAccessFromFileURLs = true
            allowUniversalAccessFromFileURLs = true
            cacheMode = WebSettings.LOAD_DEFAULT  // ✅ পরিবর্তন
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            blockNetworkLoads = false              // ✅ নতুন যোগ
            userAgentString = System.getProperty("http.agent")
            setSupportZoom(false)
            builtInZoomControls = false
            displayZoomControls = false
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
            mediaPlaybackRequiresUserGesture = false
        }

        webView.setLayerType(android.view.View.LAYER_TYPE_HARDWARE, null)

        webView.addJavascriptInterface(object : Any() {

            @JavascriptInterface
            fun startGoogleLogin() {
                runOnUiThread {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("653924196494-fv62mqkuj6avv0hojlffmlabrb7vfv4b.apps.googleusercontent.com")
                        .requestEmail()
                        .build()
                    val client = GoogleSignIn.getClient(this@MainActivity, gso)
                    startActivityForResult(client.signInIntent, RC_SIGN_IN)
                }
            }

            @JavascriptInterface
            fun shareWithImage(imageUrl: String, title: String, author: String, text: String) {
                Thread {
                    try {
                        val bitmap: Bitmap? = if (imageUrl.isNotEmpty()) {
                            try {
                                val connection = URL(imageUrl).openConnection() as HttpURLConnection
                                connection.connectTimeout = 10000
                                connection.readTimeout = 10000
                                connection.instanceFollowRedirects = true
                                connection.setRequestProperty("User-Agent", "Mozilla/5.0")
                                connection.connect()
                                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                                    BitmapFactory.decodeStream(connection.inputStream)
                                } else {
                                    Log.e("ShareImage", "HTTP ${connection.responseCode} — $imageUrl")
                                    null
                                }
                            } catch (e: Exception) {
                                Log.e("ShareImage", "Download failed: ${e.message}")
                                null
                            }
                        } else null

                        val imageUri: Uri? = bitmap?.let {
                            try {
                                val imagesDir = File(cacheDir, "images").also { d -> d.mkdirs() }
                                val imageFile = File(imagesDir, "share_cover.jpg")
                                FileOutputStream(imageFile).use { out ->
                                    it.compress(Bitmap.CompressFormat.JPEG, 90, out)
                                }
                                FileProvider.getUriForFile(
                                    this@MainActivity,
                                    "${packageName}.provider",
                                    imageFile
                                )
                            } catch (e: Exception) {
                                Log.e("ShareImage", "FileProvider failed: ${e.message}")
                                null
                            }
                        }

                        runOnUiThread {
                            val intent = if (imageUri != null) {
                                Intent(Intent.ACTION_SEND).apply {
                                    type = "image/jpeg"
                                    putExtra(Intent.EXTRA_STREAM, imageUri)
                                    putExtra(Intent.EXTRA_TEXT, text)
                                    putExtra(Intent.EXTRA_SUBJECT, title)
                                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                }
                            } else {
                                Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, text)
                                    putExtra(Intent.EXTRA_SUBJECT, title)
                                }
                            }
                            startActivity(Intent.createChooser(intent, "শেয়ার করুন — $title"))
                        }

                    } catch (e: Exception) {
                        Log.e("ShareImage", "Share crashed: ${e.message}")
                        runOnUiThread {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, text)
                                putExtra(Intent.EXTRA_SUBJECT, title)
                            }
                            startActivity(Intent.createChooser(intent, "শেয়ার করুন — $title"))
                        }
                    }
                }.start()
            }

            @JavascriptInterface
            fun isOnline(): Boolean = checkInternet()

            @JavascriptInterface
            fun getAppVersion(): String = "2.0.0"

        }, "AndroidBridge")

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.visibility = View.VISIBLE
                webView.evaluateJavascript("window.isAndroidApp=true;window.AndroidNative=true;", null)
                checkInternetAndShowBar()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
                webView.evaluateJavascript("window.isAndroidApp=true;window.AndroidNative=true;", null)
                checkInternetAndShowBar()
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                progressBar.visibility = View.GONE
                checkInternetAndShowBar()
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressBar.progress = newProgress
            }

            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                this@MainActivity.filePathCallback?.onReceiveValue(null)
                this@MainActivity.filePathCallback = filePathCallback
                val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/*"
                }
                startActivityForResult(Intent.createChooser(intent, "ছবি সিলেক্ট করুন"), FILE_CHOOSER_RESULT_CODE)
                return true
            }

            override fun onCreateWindow(
                view: WebView?,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: android.os.Message?
            ): Boolean = true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            try {
                val account = GoogleSignIn.getSignedInAccountFromIntent(data)
                    .getResult(ApiException::class.java)
                val idToken = account?.idToken
                if (idToken != null) {
                    webView.evaluateJavascript("window.firebaseSignInWithToken('$idToken');", null)
                }
            } catch (e: ApiException) {
                webView.evaluateJavascript(
                    "document.getElementById('authScreenError').textContent='Error: ${e.statusCode}';", null
                )
            }
        }

        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            val results = if (resultCode == Activity.RESULT_OK && data != null)
                arrayOf(Uri.parse(data.dataString)) else null
            filePathCallback?.onReceiveValue(results)
            filePathCallback = null
        }
    }

    private fun checkInternet(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val caps = cm.getNetworkCapabilities(cm.activeNetwork ?: return false) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun checkInternetAndShowBar() {
        offlineBar.visibility = if (checkInternet()) View.GONE else View.VISIBLE
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack() else super.onBackPressed()
    }
}