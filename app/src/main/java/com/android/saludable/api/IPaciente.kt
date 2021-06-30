package com.android.saludable.api

import com.android.saludable.data.PacienteModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IPaciente {

    @GET("paciente/{uid}")
    fun getPaciente(@Path("uid") uid : String) : Call<PacienteModel>
}