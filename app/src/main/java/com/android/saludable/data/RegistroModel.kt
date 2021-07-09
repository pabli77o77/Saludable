package com.android.saludable.data

import java.text.SimpleDateFormat
import java.util.*

data class RegistroModel (
    var bebida : String? = "",
    var comidaPrincipal : String? = "",
    var comidaSecundaria : String? = "",
    var fechaRegistro : String? = "",
    var hambre : Boolean = false,
    var ingirioPostre : Boolean = false,
    var otroAlimento : String? = "",
    var postre : String? = "",
    var tentacionOtroAlimento : Boolean = false,
    var tipoComida : String? = ""
) : Comparable<RegistroModel> {

    override fun compareTo(other: RegistroModel): Int {
        var sdf : SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        var fecha : Date = sdf.parse(other.fechaRegistro)
        var fechaActual : Date = sdf.parse(fechaRegistro)

        var diff = getDateDifferenceInDays(fecha, fechaActual)

        if(fecha.compareTo(fechaActual) > 0){
            System.out.println("Valor fecha= $fecha y valor fechaActual= $fechaActual")
        }
        return -1
    }

    fun getDateDifferenceInDays(date1: Date?, date2: Date) : Long {


        // Get the date in milliseconds
        val millis1: Long? = date1?.time
        val millis2: Long = date2.time


        // Calculate difference in milliseconds
        val diff = millis2 - millis1!!

        val seconds = diff /1000
        val minutes = diff / (60 * 1000)
        val hours = minutes / 60

        // Calculate difference in days
        val diffDays = diff / (24 * 60 * 60 * 1000)

        return diffDays
    }
}


