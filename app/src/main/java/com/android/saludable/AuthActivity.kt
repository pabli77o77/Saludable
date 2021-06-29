package com.android.saludable

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.saludable.databinding.ActivityAuthBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding
    private lateinit var userId : String
    private var LOCAL_DB : String = "LocalDb"
    private var SHARED_USER_ID : String = "user_id"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()
        getUserLogged()
    }

    private fun setUp() {

        binding.btnSignup.setOnClickListener {

            if (binding.txtEmail.text.toString().isNotEmpty() && binding.txtPasword?.text.toString().isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        binding.txtEmail.text.toString(),
                        binding.txtPasword.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            // Guardo el id en las Shared Preference
                            saveUserLogged(it.result?.user?.uid.toString())
                            showHome(it.result?.user?.email ?: "")
                        } else {
                            showAlert()
                        }
                    }
            }
        }

        binding.btnLogin.setOnClickListener {
            if (binding.txtEmail.text.toString().isNotEmpty() && binding.txtPasword.text.toString().isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        binding.txtEmail.text.toString(),
                        binding.txtPasword.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            userId = it.result?.user?.uid.toString()
                            saveUserLogged(userId)
                            showHome(userId ?: "")
                        } else {

                            showAlert(it.result.toString())
                        }
                    }
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se produjo un error autenticando al usuario.")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showAlert(mensaje : String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(userId: String) {
        val mainIntent : Intent = Intent(this, MainActivity::class.java).apply {
            putExtra("uid", userId)
        }
        startActivity(mainIntent)
    }

    private fun getUserLogged() {

        val pref = applicationContext.getSharedPreferences(LOCAL_DB, 0)
        userId = pref.getString(SHARED_USER_ID, "").toString()
        if(userId.isNotEmpty()) {
            showHome(userId)
        }
    }

    private fun saveUserLogged(userId:String) {
        val pref = applicationContext.getSharedPreferences(LOCAL_DB, 0)
        pref.edit().putString("user_id", userId).apply()
    }
}