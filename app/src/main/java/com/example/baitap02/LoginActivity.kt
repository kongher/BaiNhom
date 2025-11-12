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

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val emailEditText = findViewById<EditText>(R.id.edit_text_email_login)
        val passwordEditText = findViewById<EditText>(R.id.edit_text_password_login)
        val loginButton = findViewById<Button>(R.id.button_login)
        val registerLink = findViewById<TextView>(R.id.text_view_register_link)
        val forgotPasswordLink = findViewById<TextView>(R.id.text_view_forgot_password_link)

        // Tự động chuyển hướng nếu đã đăng nhập
        if (auth.currentUser != null && auth.currentUser!!.isEmailVerified) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        registerLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        forgotPasswordLink.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        // Xử lý logic Đăng nhập
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Email và Mật khẩu.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null && user.isEmailVerified) {
                            // 1. Đăng nhập thành công VÀ email đã được kích hoạt
                            Toast.makeText(this, "Đăng nhập thành công.", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            // 2. Đăng nhập thành công NHƯNG email CHƯA được kích hoạt
                            Toast.makeText(this,
                                "Tài khoản chưa được kích hoạt. Vui lòng kiểm tra email của bạn.",
                                Toast.LENGTH_LONG).show()
                            auth.signOut() // Buộc đăng xuất để người dùng kích hoạt
                        }
                    } else {
                        // Đăng nhập thất bại
                        Toast.makeText(this,
                            "Đăng nhập thất bại: ${task.exception?.message}",
                            Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}