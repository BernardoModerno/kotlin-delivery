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
import com.google.gson.Gson
import com.optic.deliverykotlinudemy.R
import com.optic.deliverykotlinudemy.activities.client.home.ClientHomeActivity
import com.optic.deliverykotlinudemy.models.ResponseHttp
import com.optic.deliverykotlinudemy.models.User
import com.optic.deliverykotlinudemy.providers.UsersProvider
import com.optic.deliverykotlinudemy.utils.SharedPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var imageViewGoToRegister: ImageView? = null
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null
    var buttonLogin: Button? = null
    var usersProvider = UsersProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageViewGoToRegister = findViewById(R.id.imageview_go_to_register)
        editTextEmail = findViewById(R.id.edittext_email)
        editTextPassword = findViewById(R.id.edittext_password)
        buttonLogin = findViewById(R.id.btn_login)

        imageViewGoToRegister?.setOnClickListener { goToRegister() }
        buttonLogin?.setOnClickListener { login() }

        getUserFromSession()
    }

    private fun login() {
        val email = editTextEmail?.text.toString() // NULL POINTER EXCEPTION
        val password = editTextPassword?.text.toString()

        if (isValidForm(email, password)) {

            usersProvider.login(email, password)?.enqueue(object: Callback<ResponseHttp> {
                override fun onResponse(call: Call<ResponseHttp>, response: Response<ResponseHttp>) {

                    Log.d("MainActivity", "Response: ${response.body()}")

                    if (response.body()?.isSuccess == true) {
                        Toast.makeText(this@MainActivity, response.body()?.message, Toast.LENGTH_LONG).show()
                        saveUserInSession(response.body()?.data.toString())
                        goToClientHome()
                    }
                    else {
                        Toast.makeText(this@MainActivity, "Os dados não estão corretos", Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(p0: Call<ResponseHttp>, t: Throwable) {
                    Log.d("MainActivity", "Houve um erro ${t.message}")
                    Toast.makeText(this@MainActivity, "Houve um erro ${t.message}", Toast.LENGTH_LONG).show()
                }

            })

        }
        else {
            Toast.makeText(this, "Não é válido", Toast.LENGTH_LONG).show()
        }

//        Log.d("MainActivity", "El password es: $password")
    }

    private fun goToClientHome() {
        val i = Intent(this, ClientHomeActivity::class.java)
        startActivity(i)
    }

    private fun saveUserInSession(data: String) {

        val sharedPref = SharedPref(this)
        val gson = Gson()
        val user = gson.fromJson(data, User::class.java)
        sharedPref.save("user", user)
    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    private fun getUserFromSession() {

        val sharedPref = SharedPref(this)
        val gson = Gson()

        if (!sharedPref.getData("user").isNullOrBlank()) {
            // SI EL USARIO EXISTE EN SESION
            val user = gson.fromJson(sharedPref.getData("user"), User::class.java)
            goToClientHome()
        }

    }

    private fun isValidForm(email: String, password: String): Boolean {

        if (email.isBlank()) {
            return false
        }

        if (password.isBlank()) {
            return false
        }

        if (!email.isEmailValid()) {
            return false
        }

        return true
    }

    private fun goToRegister() {
        val i = Intent(this, RegisterActivity::class.java)
        startActivity(i)
    }


}