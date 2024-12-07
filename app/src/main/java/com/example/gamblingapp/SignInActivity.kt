package com.example.gamblingapp
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var forgotPasswordTextView: TextView
    private lateinit var registerTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // View id part
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signInButton = findViewById(R.id.signInButton)
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView)
        registerTextView = findViewById(R.id.registerTextView)

        // Sign In button
        signInButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // User verification process
            // i made 2 admin accounts
            if (email == "artun.tonguc@example.com" && password == "password" ||
                email=="kacper.kubiak@example.com" && password == "password") {
                val intent = Intent(this, GameMenuActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }

        // Link of forgot password
        forgotPasswordTextView.setOnClickListener {
            // Password reset process (sending an e-mail will be done later)
            Toast.makeText(this, "Password reset link sent to your email", Toast.LENGTH_SHORT).show()
        }

        // Register
        registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        val sharedPreferences: SharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val isFirstLogin = sharedPreferences.getBoolean("isFirstLogin",true)

        if (isFirstLogin){
            val editor = sharedPreferences.edit()
            editor.putInt("userBalance",1000)
            editor.putBoolean("isFirstLogin",false)
            editor.apply()


            Toast.makeText(this,"Welcome! Your balance is 1000$",Toast.LENGTH_SHORT).show()
        }

    }
}