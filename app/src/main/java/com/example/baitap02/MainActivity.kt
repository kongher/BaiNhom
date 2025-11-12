package com.example.baitap02

// File: MainActivity.kt
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        // KHẮC PHỤC LỖI #1: Kiểm tra người dùng trước khi hiển thị
        if (currentUser == null) {
            // Nếu không có người dùng hoặc phiên bị lỗi, buộc quay lại Login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return // Thoát khỏi onCreate
        }

        val welcomeText = if (currentUser.email.isNullOrEmpty()) {
            "Xin chào! (Email không khả dụng)"
        } else {
            "Xin chào: ${currentUser.email}"
        }

        // Hiển thị thông tin người dùng
        findViewById<TextView>(R.id.text_view_welcome).text = welcomeText

        // Nút Đăng xuất
        findViewById<Button>(R.id.button_logout).setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}