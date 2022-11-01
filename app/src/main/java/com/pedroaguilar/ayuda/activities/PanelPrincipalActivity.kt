package com.pedroaguilar.ayuda.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.pedroaguilar.ayuda.R
import com.pedroaguilar.ayuda.databinding.ActivityPanelPrincipalBinding

class PanelPrincipalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPanelPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPanelPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val bottomNavigationView = binding.bottomNavigationView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_panel_principal) as NavHostFragment

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.favoritesFragment,
            R.id.perfilFragment,
            R.id.categoriasDonarFragment,
            R.id.busquedasFragment,
            R.id.settingsFragment
        ))

        val navController = navHostFragment.navController

        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNavigationView.setupWithNavController(navController)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_host_fragment_content_panel_principal){
            finish()
        }else if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}