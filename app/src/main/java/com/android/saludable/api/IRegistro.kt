package com.android.saludable.api

import com.android.saludable.data.RegistroModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface IRegistro {


    @PATCH("alimentacion/{id}")
    fun insertarRegistro(
        @Path("id") id : String,
        @Body registro : RegistroModel) : Call<RegistroModel>

    @POST("alimentacion/{id}")
    fun insertarRegistroPost(
        @Path("id") id : String,
        @Body registro : RegistroModel) : Call<RegistroModel>

}