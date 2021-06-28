package com.android.saludable.data

data class PacienteDataSource(
    val nombre : String? = null,
    val apellido : String? = null,
    val dni : String? = null,
    val sexo : String? = null,
    val fechaNacimiento : String? = null,
    val localidad : String? = null,
    val tipoTratamiento : String? = null,
    val usuario : String? = null
)
