package com.appexperts.supergit.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.OAuthProvider
import com.appexperts.supergit.R
import kotlinx.android.synthetic.main.fragment_add_user_repository.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "AddUserRepositoryGithub"
class AddUserRepositoryGithubFragment : BaseAuthFragment() {

    private val loginGithubViewModel: AddUserRepositoryGithubViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_user_repository, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add_user_git_hub_buttom.setOnClickListener {
            uiCommunicatorInterface.showGithubLogin()
        }


    }


}