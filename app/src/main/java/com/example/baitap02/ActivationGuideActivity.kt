package com.example.baitap02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ActivationGuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activation_guide)

        val loginButton = findViewById<Button>(R.id.button_go_to_login)

        loginButton.setOnClickListener {
            // Chuyển người dùng quay lại màn hình đăng nhập
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}