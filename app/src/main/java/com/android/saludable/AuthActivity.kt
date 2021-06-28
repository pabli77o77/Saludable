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

class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUp()
    }

    private fun setUp() {
        title = "Autenticación"

        binding.btnSignup.setOnClickListener {

            if (binding.txtEmail.text.toString().isNotEmpty() && binding.txtPasword?.text.toString().isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        binding.txtEmail.text.toString(),
                        binding.txtPasword.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            // Guardo el id en las Shared Preference
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
                            // Ahora puedo guardar el Uid en las shared_preference y usarlo cuando lo necesite
                            Toast.makeText(applicationContext, "Uid: ${it.result?.user?.uid}", Toast.LENGTH_SHORT).show()
                            showHome(it.result?.user?.email ?: "")
                        } else {
                            showAlert()
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

    private fun showHome(email: String) {
        val homeIntent : Intent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", binding.txtEmail.text.toString())
        }
        startActivity(homeIntent)
    }
}