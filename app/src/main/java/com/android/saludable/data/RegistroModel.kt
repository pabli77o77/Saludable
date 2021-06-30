package com.android.saludable.data

import java.util.*

data class RegistroModel (
    var tipoComida : String? = null,
    var comidaPrincipal : String? = null,
    var comidaSecundaria : String? = null,
    var bebida : String? = null,
    var ingirioPostre : Boolean = false,
    var postre : String? = null,
    var tentacionOtroAlimento : Boolean = false,
    var otroAlimento : String? = null,
    var hambre : Boolean = false,
    var fechaRegistro : String? = null
)
