package com.android.saludable.data

data class PacienteModel(
    var nombre : String? = null,
    var apellido : String? = null,
    var dni : String? = null,
    var sexo : String? = null,
    var fechaNacimiento : String? = null,
    var localidad : String? = null,
    var tipoTratamiento : String? = null,
    var usuario : String? = null
)
