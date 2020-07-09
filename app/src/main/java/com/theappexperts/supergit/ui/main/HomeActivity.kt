package com.theappexperts.supergit.ui.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.theappexperts.supergit.R
import com.theappexperts.supergit.ui.auth.BaseAuthFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import org.koin.android.ext.android.inject


private const val TAG = "HomeActivity"
class HomeActivity : AppCompatActivity(), BaseAuthFragment.CommunicatorsInterface {

    private lateinit var navController: NavController
    val firebaseAuth: FirebaseAuth by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpActionBar()
        setUpNavController()
        setUpBottomnNavView()

    }



    private fun setUpNavController() {
        navController = findNavController(R.id.nav_host_fragment)
    }

    private fun setUpBottomnNavView() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)


        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_users, R.id.navigation_add, R.id.navigation_favourites)
        )
        navView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val navOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_right)
            .setPopExitAnim(R.anim.slide_out_left)
            .setPopUpTo(navController.graph.startDestination, false)
            .build()
        navView.setOnNavigationItemSelectedListener() { item ->
            var handled = false
            if (navController.graph.findNode(item.itemId) != null) {
                navController.navigate(item.itemId, null, navOptions)
                handled = true
            }
            handled
        }
    }

    private fun setUpActionBar() {
        val tb: Toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(tb)

    }

    override fun showProgressBar() {
        TODO("Not yet implemented")
    }

    override fun hideProgressBar() {
        TODO("Not yet implemented")
    }

    override fun showGithubLogin() {



            val provider: OAuthProvider.Builder = OAuthProvider.newBuilder("github.com")

            FirebaseAuth.getInstance()
                .startActivityForSignInWithProvider(this, provider.build())
                .addOnSuccessListener {

                }
                .addOnFailureListener {exeption ->
                    Log.i(TAG, "startActivityForSignInWithGithub: $exeption")
                }

    }
}