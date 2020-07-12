package com.theappexperts.supergit.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.OAuthProvider
import com.theappexperts.supergit.R
import com.theappexperts.supergit.models.GitUser
import com.theappexperts.supergit.ui.BaseFragment
import com.theappexperts.supergit.ui.addUser.AddUserRepositoryGithubViewModel
import com.theappexperts.supergit.ui.addUser.BaseAuthFragment
import com.theappexperts.supergit.utils.Resource
import com.theappexperts.supergit.utils.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


private const val TAG = "HomeActivity"

class HomeActivity : AppCompatActivity(),
    BaseAuthFragment.CommunicatorsInterface,
    BaseFragment.CommunicatorsInterface {

    private var bottomNavigationView: BottomNavigationView? = null
    private var currentNavController: LiveData<NavController>? = null

    val firebaseAuth: FirebaseAuth by inject()
    val addUserRepositoryGithubViewModel: AddUserRepositoryGithubViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpActionBar()
        if (savedInstanceState == null)
            setUpBottomNabView()

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setUpBottomNabView()
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    private fun setUpBottomNabView() {
        bottomNavigationView = findViewById(R.id.nav_view)
        val navGraphIds = listOf(R.navigation.add, R.navigation.home, R.navigation.favourites)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView?.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_fragment,
            intent = intent
        )
        // Whenever the selected controller changes, setup the action bar.
        controller?.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller

    }

    private fun setUpActionBar() {
        val tb: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(tb)
    }

    override fun showProgressBar() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressbar.visibility = View.GONE
    }

    //Function that navigate to another graph given the navGraphId
    override fun navigateToGraph(navGraphId: Int) {
        when (navGraphId) {
            R.id.add -> {
                bottomNavigationView?.selectedItemId = R.id.add
            }
            R.id.home -> {
                bottomNavigationView?.selectedItemId = R.id.home
            }
            R.id.favourites -> {
                bottomNavigationView?.selectedItemId = R.id.favourites
            }
            else ->
                IllegalArgumentException("I could not find the graph ")
        }

    }

    override fun startActivityFromFragment(
        fragment: Fragment,
        intent: Intent?,
        requestCode: Int,
        options: Bundle?
    ) {
        super.startActivityFromFragment(fragment, intent, requestCode, options)
        Log.i(TAG, "startActivityFromFragment: ")

    }

    override fun showGithubLogin() {


        val provider: OAuthProvider.Builder = OAuthProvider.newBuilder("github.com")

        val scopes: List<String> = listOf("repo")
        provider.scopes = scopes;

        firebaseAuth
            .startActivityForSignInWithProvider(this, provider.build())
            .addOnSuccessListener {
                Log.i(
                    TAG,
                    "startActivityForSignInWithGithub: ${(it.credential as OAuthCredential).accessToken}"
                )
                insertUserAndMoveToHomePage(it)
            }
            .addOnFailureListener { exeption ->
                Log.i(TAG, "startActivityForSignInWithGithub: $exeption")
                //  showErrorGithub()
            }


    }

    private fun insertUserAndMoveToHomePage(it: AuthResult) {
        val name2 :String = it.additionalUserInfo?.profile?.get("login").toString()
        val name = it.user?.displayName ?: ""
        val photo: Uri = it.user?.photoUrl ?: Uri.Builder().build()
        val token = (it.credential as OAuthCredential).accessToken

        addUserRepositoryGithubViewModel.insertUser(GitUser(name2, photo, token))
            .observe(this, Observer {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        Log.i(TAG, "showGithubLogin: success")
                        hideProgressBar()
                        navigateToGraph(R.id.home)
                    }
                    Resource.Status.ERROR -> {
                        Log.i(TAG, "showGithubLogin: error")
                        showErrorGithub()
                        hideProgressBar()
                    }
                    Resource.Status.LOADING ->{
                        showProgressBar()
                    }

                }
            })
    }

    private fun showErrorGithub() {

        Toast.makeText(this, getString(R.string.error_login_github_process_msg), Toast.LENGTH_LONG)
            .show()
    }

}