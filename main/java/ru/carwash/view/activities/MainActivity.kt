package ru.carwash.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.carwash.carwash.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.carwash.controllers.DataProcessor.Companion.getBoolean
import ru.carwash.view.setupWithNavController

class MainActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    companion object {
        @JvmField
        var TAG = "CAR_WASH"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.CarWashTheme)
        if (!getBoolean(this, "isLogin", false)) {
            val loginIntent = Intent(this, WelcomeActivity::class.java)
            startActivity(loginIntent)
            finish()
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_bar)

        val navGraphIds = listOf(R.navigation.cars_graph, R.navigation.orders_graph, R.navigation.map_graph, R.navigation.profile_graph)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = supportFragmentManager,
                containerId = R.id.fragment_container,
                intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            //setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}