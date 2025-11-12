package com.example.baitap02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        val emailEditText = findViewById<EditText>(R.id.edit_text_email_register)
        val passwordEditText = findViewById<EditText>(R.id.edit_text_password_register)
        val registerButton = findViewById<Button>(R.id.button_register)
        val loginLink = findViewById<TextView>(R.id.text_view_login_link)

        // Chuyển sang màn hình Đăng nhập
        loginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Xử lý logic Đăng ký
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Email và Mật khẩu.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // 1. Đăng ký thành công
                        val user = auth.currentUser

                        // 2. Gửi email kích hoạt (OTP qua email)
                        user?.sendEmailVerification()
                            ?.addOnCompleteListener { emailTask ->
                                if (emailTask.isSuccessful) {
                                    Toast.makeText(this,
                                        "Đăng ký thành công. Đã gửi link kích hoạt đến email của bạn.",
                                        Toast.LENGTH_LONG).show()

                                    // Chuyển sang màn hình hướng dẫn kích hoạt
                                    startActivity(Intent(this, ActivationGuideActivity::class.java))
                                    finish()

                                } else {
                                    Toast.makeText(this, "Lỗi gửi email kích hoạt.", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        // Đăng ký thất bại (ví dụ: email đã tồn tại, mật khẩu quá yếu)
                        Toast.makeText(this,
                            "Đăng ký thất bại: ${task.exception?.message}",
                            Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}