package com.android.saludable

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.saludable.databinding.ActivityAuthBinding
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {

    //private val btnSignup : Button = findViewById(R.id.btnSignup)
    //private val btnLogin : Button = findViewById(R.id.btnLogin)
    //private val etEmail : TextView = findViewById(R.id.etEmailLogin)
    //private val etPassword : TextView = findViewById(R.id.etContraseña)
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
            if (binding.etEmailLogin.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        binding.etEmailLogin.text.toString(),
                        binding.etPassword.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "")
                        } else {
                            showAlert()
                        }
                    }
            }
        }

        binding.btnLogin.setOnClickListener {
            if (binding.etEmailLogin.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        binding.etEmailLogin.text.toString(),
                        binding.etPassword.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
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
            putExtra("email", binding.etEmailLogin.text.toString())
        }
        startActivity(homeIntent)
    }
}