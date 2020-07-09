package com.theappexperts.supergit.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.theappexperts.supergit.R
import com.theappexperts.supergit.models.GitUser
import com.theappexperts.supergit.ui.user.SearchUserAdapter
import com.theappexperts.supergit.ui.user.UserItemAdapter
import com.theappexperts.supergit.utils.ERROR_INSERTING
import com.theappexperts.supergit.utils.Resource
import com.theappexperts.supergit.utils.Resource.Status
import com.theappexperts.supergit.utils.Resource.Status.*
import com.theappexperts.supergit.utils.getQueryTextChangeStateFlow.getQueryTextChangeStateFlow
import kotlinx.android.synthetic.main.fragment_add_user_repository.*
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "AddUserRepositoryGithub"

class AddUserRepositoryGithubFragment : BaseAuthFragment(), UserItemAdapter.UserItemsListener {

    private val searchUserAdapter: SearchUserAdapter =
        SearchUserAdapter(object : SearchUserAdapter.SearchUserListener {
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
        findNavController().navigate(R.id.action_navigation_add_to_navigation_users)
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
            Observer { list_user ->
                when (list_user.status) {
                    SUCCESS -> {
                        setCurrentUserAdapter(list_user.data?.peekContent()!!)
                    }

                    LOADING -> {

                    }

                    ERROR -> {

                    }

                }

            })
    }

    private fun setCurrentUserAdapter(listUser: List<GitUser>) {
        searchUserAdapter.list = listUser
    }

    private fun initUserAdapter() {
        searching_user_recyclerView.adapter = searchUserAdapter
    }

    override fun remove(user: GitUser, position: Int) {

    }

    override fun onClickItem(user: GitUser) {

    }


}