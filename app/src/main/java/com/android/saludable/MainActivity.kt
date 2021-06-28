package com.android.saludable

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.android.saludable.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigationDrawer()
        changeFragment(InicioFragment())


    }

    private fun initNavigationDrawer() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        toggle.isDrawerIndicatorEnabled = true
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)
    }

    private fun changeFragment(frag : Fragment) {
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment_container, frag).commit()
    }

    private fun setToolbarTitle(title:String) {
        supportActionBar?.title = title
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        when(item.itemId){
            R.id.nav_item_inicio -> changeFragment(InicioFragment())//Toast.makeText(applicationContext, "Inicio", Toast.LENGTH_SHORT).show()
            R.id.nav_item_registro -> changeFragment(RegistroFragment())//Toast.makeText(applicationContext, "Registro", Toast.LENGTH_SHORT).show()
            R.id.nav_item_settings ->  changeFragment(SettingsFragment())//Toast.makeText(applicationContext, "Settings", Toast.LENGTH_SHORT).show()
            R.id.nav_item_mis_datos -> changeFragment(UserFragment())
        }
        setToolbarTitle(item.title.toString())
        return true
    }

}