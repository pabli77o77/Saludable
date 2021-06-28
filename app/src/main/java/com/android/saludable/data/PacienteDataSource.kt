package com.android.saludable.data

import com.google.firebase.database.Exclude

data class PacienteDataSource(
    @get:Exclude
    var id : String? = null,
    var nombre : String? = null,
    var apellido : String? = null,
    var dni : String? = null,
    var sexo : String? = null,
    var fechaNacimiento : String? = null,
    var localidad : String? = null,
    var tipoTratamiento : String? = null,
    var usuario : String? = null
)
