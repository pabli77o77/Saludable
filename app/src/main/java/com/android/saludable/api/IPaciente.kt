package com.android.saludable.api

import com.android.saludable.data.PacienteModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface IPaciente {
    @GET(".")
    fun getPaciente() : Call<PacienteModel>

    @GET
    fun getPaciente(@Url url : String) : Call<PacienteModel>

    @GET
    suspend fun getPaciente2(@Url url : String) : Response<PacienteModel>

    @GET("HAKWDAUIK9bhV7gRzLWPddFGUux1/.json")
    fun getPaciente3() : Call<PacienteModel>

    @GET("paciente/{uid}")
    fun getPaciente4(@Path("uid") uid : String) : Call<PacienteModel>


}