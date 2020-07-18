package com.javabobo.supergit.ui.addUser

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.javabobo.supergit.R
import com.javabobo.supergit.models.GitUser
import com.javabobo.supergit.ui.addUser.adapter.SearchUserAdapter
import com.javabobo.supergit.utils.ERROR_INSERTING
import com.javabobo.supergit.utils.Resource.Status.*
import kotlinx.android.synthetic.main.fragment_add_user_repository.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "AddUserRepositoryGithub"

class AddUserRepositoryGithubFragment : BaseAddUserFragment() {

    private val searchUserAdapter: SearchUserAdapter =
        SearchUserAdapter(object :
            SearchUserAdapter.SearchUserListener {
            override fun onClickItem(user: GitUser) {
                addUserRepositoryGithubViewModel.insertUser(user)
                    .observe(viewLifecycleOwner, Observer { result ->
                        when (result.status) {
                            SUCCESS -> {
                                navigateToHomeFragment()
                            }
                            ERROR -> {
                                showErrorToInsert()
                            }

                        }

                    })
            }
        })

    private fun showErrorToInsert() {
        Toast.makeText(requireContext(), ERROR_INSERTING, Toast.LENGTH_LONG).show()
    }

    private fun navigateToHomeFragment() {
        uiCommunicatorInterface?.navigateToGraph(R.id.home)
    }

    private val addUserRepositoryGithubViewModel: AddUserRepositoryGithubViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_user_repository, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUserAdapter()
        initListeners()
        subscribeSearchUser()
        initListeners()
        requestFocus()


    }

    private fun requestFocus() {
        request_focus_view.requestFocus()
    }

    private fun initListeners() {
        add_user_git_hub_buttom.setOnClickListener {
            uiCommunicatorInterface.showGithubLogin()
        }
        val watcher = object : TextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim()
                GlobalScope.launch {
                    delay(500)  //debounce timeOut
                    addUserRepositoryGithubViewModel.searchUser(searchText)
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit
        }

        user_name_edittext.addTextChangedListener(watcher)

    }



    private fun subscribeSearchUser() {
        addUserRepositoryGithubViewModel.searchUser.observe(
            requireActivity(),
            Observer { result ->
                //If the data has been handle ignore it...
                result.data?.getContentIfNotHandled()?.let { list_user->
                    when (result.status) {
                        SUCCESS -> {
                            setCurrentUserAdapter(list_user)
                            hideProgressBar()
                        }

                        LOADING -> {
                            showProgressBar()
                        }

                        ERROR -> {
                            hideProgressBar()
                        }

                    }
                }

            })
    }

    private fun showProgressBar() {
        progress_bar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progress_bar?.visibility = View.GONE
    }

    private fun setCurrentUserAdapter(listUser: List<GitUser>) {
        searchUserAdapter.list = listUser
    }

    private fun initUserAdapter() {
        searching_user_recyclerView.adapter = searchUserAdapter
    }


}