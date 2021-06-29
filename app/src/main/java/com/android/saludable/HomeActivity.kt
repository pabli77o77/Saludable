package com.android.saludable

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.saludable.databinding.ActivityAuthBinding
import com.android.saludable.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("usuario")
        setUp(email?:"")
    }

    private fun setUp(email: String) {
        title = "Inicio"
        binding.tvUsuario.text = email

        logout()
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}