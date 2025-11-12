package com.example.baitap02


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password) // Bây giờ gọi đúng hàm gốc

        auth = Firebase.auth

        val emailEditText = findViewById<EditText>(R.id.edit_text_forgot_email)
        val sendOtpButton = findViewById<Button>(R.id.button_send_otp_forgot)

        sendOtpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập Email.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Gửi email đặt lại mật khẩu (link OTP qua email)
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,
                            "Đã gửi email chứa link đặt lại mật khẩu đến: $email",
                            Toast.LENGTH_LONG).show()
                        finish() // Đóng màn hình
                    } else {
                        Toast.makeText(this,
                            "Lỗi: Email không tồn tại hoặc lỗi hệ thống. ${task.exception?.message}",
                            Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    // ĐÃ XÓA: private fun setContentView(activityForgotPassword: Int) {}
}