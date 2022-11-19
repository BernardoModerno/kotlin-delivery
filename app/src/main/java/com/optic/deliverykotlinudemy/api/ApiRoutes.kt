package com.optic.deliverykotlinudemy.api

import com.optic.deliverykotlinudemy.routes.UsersRoutes
import retrofit2.Retrofit

class ApiRoutes {

    val API_URL = "http://172.24.0.1:3000/api/"
    val retrofit = RetrofitClient()

    fun getUsersRoutes(): UsersRoutes {
        return retrofit.getClient(API_URL).create(UsersRoutes::class.java)
    }

}