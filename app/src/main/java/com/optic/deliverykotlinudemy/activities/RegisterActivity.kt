package com.optic.deliverykotlinudemy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.optic.deliverykotlinudemy.R

class RegisterActivity : AppCompatActivity() {

    val TAG = "RegisterActivity"

    var imageViewGoToLogin: ImageView? = null
    var editTextName: EditText? = null
    var editTextLastname: EditText? = null
    var editTextEmail: EditText? = null
    var editTextPhone: EditText? = null
    var editTextPassword: EditText? = null
    var editTextConfirmPassword: EditText? = null
    var buttonRegister: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        imageViewGoToLogin = findViewById(R.id.imageview_go_to_login)
        editTextName = findViewById(R.id.edittext_name)
        editTextLastname = findViewById(R.id.edittext_lastname)
        editTextEmail = findViewById(R.id.edittext_email)
        editTextPhone = findViewById(R.id.edittext_phone)
        editTextConfirmPassword = findViewById(R.id.edittext_confirm_password)
        editTextPassword = findViewById(R.id.edittext_password)
        buttonRegister = findViewById(R.id.btn_register)


        imageViewGoToLogin?.setOnClickListener { goToLogin() }
        buttonRegister?.setOnClickListener { register() }
    }

    private fun register() {
        val name = editTextName?.text.toString()
        val lastname = editTextLastname?.text.toString()
        val email = editTextEmail?.text.toString()
        val phone = editTextPhone?.text.toString()
        val password = editTextPassword?.text.toString()
        val confirmPassword = editTextConfirmPassword?.text.toString()

        Log.d(TAG, "O nome é: $name")
        Log.d(TAG, "O sobrenome é: $lastname")
        Log.d(TAG, "O email é: $email")
        Log.d(TAG, "O Telefone é: $phone")
        Log.d(TAG, "O password é: $password")
        Log.d(TAG, "O confirm password é: $confirmPassword")

    }

    private fun goToLogin() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}