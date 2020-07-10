package com.theappexperts.supergit.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.theappexperts.supergit.R
import com.theappexperts.supergit.ui.auth.BaseAuthFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.theappexperts.supergit.utils.setupWithNavController
import org.koin.android.ext.android.inject


private const val TAG = "HomeActivity"

class HomeActivity : AppCompatActivity(), BaseAuthFragment.CommunicatorsInterface {

    private var currentNavController: LiveData<NavController>? = null

    val firebaseAuth: FirebaseAuth by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpActionBar()
        setUpBottomnNavView()

    }


    private fun setUpBottomnNavView() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)
        val navGraphIds = listOf(R.navigation.mobile_navigation)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )
        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller

    }

    private fun setUpActionBar() {
        val tb: Toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(tb)

    }

    override fun showProgressBar() {
    }

    override fun hideProgressBar() {
    }

    override fun showGithubLogin() {
        
        val provider: OAuthProvider.Builder = OAuthProvider.newBuilder("github.com")

        FirebaseAuth.getInstance()
            .startActivityForSignInWithProvider(this, provider.build())
            .addOnSuccessListener {

            }
            .addOnFailureListener { exeption ->
                Log.i(TAG, "startActivityForSignInWithGithub: $exeption")
            }

    }
}