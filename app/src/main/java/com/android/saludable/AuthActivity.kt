package com.android.saludable

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.saludable.databinding.ActivityAuthBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.dialog_view.view.*

class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding
    private lateinit var userId : String
    private var LOCAL_DB : String = "LocalDb"
    private var SHARED_USER_ID : String = "user_id"
    private lateinit var firebaseUser : FirebaseUser
    private lateinit var auth : FirebaseAuth

    override fun onStart() {
        super.onStart()


    }

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
                            saveUserLogged(it.result?.user?.uid.toString())
                            alert("Login ok", "")
                            showHome(it.result?.user?.email ?: "")
                        } else {
                            Log.e(TAG, it.result.toString())
                            Toast.makeText(this,"Registro de usuario inválido", Toast.LENGTH_SHORT).show()
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
                            firebaseUser = it.result!!.user!!
                            val tk : String = firebaseUser.getIdToken(true).toString()
                            userId = it.result?.user?.uid.toString()
                            alert("Login Ok", firebaseUser.email.toString())
                            saveUserLogged(userId)
                            showHome(userId ?: "")
                        } else {
                            Log.e(TAG, it.result.toString())
                            Toast.makeText(this,it.result.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Log.e(TAG, "Campos Mail o Contraseña vacíos")
                Toast.makeText(this,"Mail o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun showHome(userId: String) {
        val mainIntent : Intent = Intent(this, MainActivity::class.java).apply {
            putExtra("uid", userId)
        }
        startActivity(mainIntent)
        finish()
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

    private fun alert(titulo: String, mensaje: String) {
        val view = View.inflate(this, R.layout.dialog_view, null)
        val builder = android.app.AlertDialog.Builder(this)
        builder.setView(view)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        view.tv_alert_titulo.setText(titulo)
        view.tv_alert_mensaje.setText(mensaje)
        view.btnConfirmAlert.setOnClickListener {
            dialog.dismiss()
        }
    }

}