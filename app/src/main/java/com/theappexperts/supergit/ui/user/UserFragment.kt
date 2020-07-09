package com.theappexperts.supergit.ui.user

import com.theappexperts.supergit.models.GitUser
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.theappexperts.supergit.R
import androidx.lifecycle.Observer
import com.theappexperts.supergit.ui.BaseFragment
import com.theappexperts.supergit.utils.Resource.Status.*
import kotlinx.android.synthetic.main.fragment_user.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "UserFragment"

class UserFragment : BaseFragment(), UserItemAdapter.UserItemsListener {

    private val currentUserItemAdapter: UserItemAdapter = UserItemAdapter(this)
    private val searchUserAdapter: SearchUserAdapter =
        SearchUserAdapter(object : SearchUserAdapter.SearchUserListener {
            override fun onClickItem(user: GitUser) {
                userViewModel.insertUser(user)
            }
        })
    private val userViewModel: UserViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUserAdapter()
        initSearchAdapter()
        subscribeGetCurrentUser()
        subscribeSearchUser()
        initListeners()

    }

    private fun initListeners() {
        search_imageView.setOnClickListener {
            val username: String = search_username_editText.text.toString().trim()
            if (username.isNotEmpty()) {
                userViewModel.searchUser(username)
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.message_empty_username),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun subscribeSearchUser() {
        userViewModel.searchUser.observe(
            requireActivity(),
            Observer { list_user ->
                when (list_user) {
                    SUCCESS -> {

                    }

                    LOADING -> {

                    }

                    ERROR -> {

                    }

                }

            })
    }

    private fun subscribeGetCurrentUser() {
        userViewModel.getCurrentUsers().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                SUCCESS -> {
                    uiCommunicatorInterface.hideProgressBar()
                    setCurrentUserAdapter(it.data?.peekContent()!!)

                }
                ERROR -> {
                    uiCommunicatorInterface.hideProgressBar()
                    showErrorMsg()
                }

                LOADING -> {
                    uiCommunicatorInterface.showProgressBar()
                }

            }

        })
    }

    private fun showErrorMsg() {
        Toast.makeText(
            requireContext(),
            getString(R.string.error_msg_to_get_current_user),
            Toast.LENGTH_LONG
        )
    }

    private fun initSearchAdapter() {
        searching_user_recyclerView.adapter = searchUserAdapter
    }

    private fun initUserAdapter() {
        users_recycler.adapter = currentUserItemAdapter
    }

    fun setCurrentUserAdapter(users: List<GitUser>) {
        currentUserItemAdapter.list = users
    }

    fun setSearchUserAdapter(users: List<GitUser>) {
        searchUserAdapter.list = users
    }

    override fun remove(user: GitUser, position: Int) {
        //TODO("Not yet implemented")
    }

    override fun onClickItem(user: GitUser) {
        //  TODO("Not yet implemented")
    }
}