package com.optic.deliverykotlinudemy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.optic.deliverykotlinudemy.R
import com.optic.deliverykotlinudemy.models.ResponseHttp
import com.optic.deliverykotlinudemy.models.User
import com.optic.deliverykotlinudemy.providers.UsersProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    var usersProvider = UsersProvider()

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
        
        if (isValidForm(name = name, phone = phone, lastname = lastname, email = email, password = password, confirmPassword = confirmPassword)) {

            val user = User(
                name = name,
                lastname = lastname,
                email = email,
                phone = phone,
                password = password
            )

            usersProvider.register(user)?.enqueue(object: Callback<ResponseHttp> {
                override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {

                    Toast.makeText(this@RegisterActivity, response.body()?.message, Toast.LENGTH_LONG).show()

                    Log.d(TAG, "Response: ${response}" )
                    Log.d(TAG, "Body: ${response.body()}" )
                }

                override fun onFailure(p0: Call<ResponseHttp>, t: Throwable) {
                    Log.d(TAG, "Houve error ${t.message}")
                    Toast.makeText(this@RegisterActivity, "Houve error ${t.message}", Toast.LENGTH_LONG).show()
                }

            })

        }

    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun isValidForm(
        name: String,
        lastname: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ): Boolean {

        if (name.isBlank()) {
            Toast.makeText(this, "Deve entrar com o nome", Toast.LENGTH_SHORT).show()
            return false
        }
        
        if (lastname.isBlank()) {
            Toast.makeText(this, "Deve entrar com o sobrenome", Toast.LENGTH_SHORT).show()
            return false
        }
        
        if (phone.isBlank()) {
            Toast.makeText(this, "Deve entrar com o telefone", Toast.LENGTH_SHORT).show()
            return false
        }
        
        if (email.isBlank()) {
            Toast.makeText(this, "Deve entrar com o email", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isBlank()) {
            Toast.makeText(this, "Deve entrar com a senha", Toast.LENGTH_SHORT).show()
            return false
        }
        
        if (confirmPassword.isBlank()) {
            Toast.makeText(this, "Deve entrar com a confirmação de senha", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!email.isEmailValid()) {
            Toast.makeText(this, "O email não é válido", Toast.LENGTH_SHORT).show()
            return false
        }
        
        if (password != confirmPassword) {
            Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
            return false
        }
        
        return true
    }

    private fun goToLogin() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}