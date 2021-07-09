package com.android.saludable.api

import com.android.saludable.data.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface IRegistro {


    @POST("alimentacion/{id}")
    fun insertarRegistroPost(
        @Path("id") id : String,
        @Body registro : RegistroModel) : Call<RegistroModel>

}