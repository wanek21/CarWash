package ru.carwash.screens.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.carwash.carwash.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import ru.carwash.controllers.DataProcessor.Companion.getBoolean
import ru.carwash.controllers.SessionManager
import ru.carwash.screens.registration.WelcomeActivity
import ru.carwash.view.setupWithNavController
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private var currentNavController: LiveData<NavController>? = null

    companion object {
        const val TAG = "CAR_WASH"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.CarWashTheme)
        super.onCreate(savedInstanceState)
        if (!viewModel.isLogged()) {
            val loginIntent = Intent(this, WelcomeActivity::class.java)
            startActivity(loginIntent)
            finish()
        }
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