package com.example.gamblingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var fullNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var birthDateEditText: EditText
    private lateinit var peselEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Defination of the all views
        fullNameEditText = findViewById(R.id.fullNameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        birthDateEditText = findViewById(R.id.birthDateEditText)
        peselEditText = findViewById(R.id.peselEditText)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val birthDate = birthDateEditText.text.toString()
            val pesel = peselEditText.text.toString()

            // Check the all fields are filled or not
            if (fullName.isNotBlank() && email.isNotBlank() && password.isNotBlank() && birthDate.isNotBlank() && pesel.isNotBlank()) {
                Toast.makeText(this, "Registered Successfully.", Toast.LENGTH_SHORT).show()
                 // If u registered succesfully turn back to signin screen
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}