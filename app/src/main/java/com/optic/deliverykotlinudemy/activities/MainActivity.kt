package com.optic.deliverykotlinudemy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.optic.deliverykotlinudemy.R

class MainActivity : AppCompatActivity() {

    var imageViewGoToRegister: ImageView? = null
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null
    var buttonLogin: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageViewGoToRegister = findViewById(R.id.imageview_go_to_register)
        editTextEmail = findViewById(R.id.edittext_email)
        editTextPassword = findViewById(R.id.edittext_password)
        buttonLogin = findViewById(R.id.btn_login)

        imageViewGoToRegister?.setOnClickListener { goToRegister() }
        buttonLogin?.setOnClickListener { login() }
    }

    private fun login() {
        val email = editTextEmail?.text.toString() // NULL POINTER EXCEPTION
        val password = editTextPassword?.text.toString()

        Toast.makeText(this, "O email é: $email", Toast.LENGTH_LONG).show()
        Toast.makeText(this, "O password é: $password", Toast.LENGTH_LONG).show()

        Log.d("MainActivity", "O email é: $email")
        Log.d("MainActivity", "O password é: $password")
    }

    private fun goToRegister() {
        val i = Intent(this, RegisterActivity::class.java)
        startActivity(i)
    }


}