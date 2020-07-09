package com.theappexperts.supergit.ui.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.theappexperts.supergit.R
import androidx.lifecycle.Observer
import com.javabobo.supergit.models.GitUser
import kotlinx.android.synthetic.main.fragment_user.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "UserFragment"

class UserFragment : Fragment(), UserItemAdapter.UserItemsListener {

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
        users_recycler.adapter = currentUserItemAdapter
        searching_user_recyclerView.adapter = searchUserAdapter
        super.onViewCreated(view, savedInstanceState)
        userViewModel.currentUsersLiveData.observe(requireActivity(), Observer { listUsers ->
            setCurrentUserAdapter(listUsers)
        })
        userViewModel.userSearchResponseLiveData.observe(requireActivity(), Observer { usersList ->
            Log.i(TAG, "on user Search Response : ${usersList?.size} ")
            setSearchUserAdapter(usersList)
        })
        search_imageView.setOnClickListener {
            val username: String = search_username_editText.text.toString().trim()
            if (username.isNotEmpty()){
                userViewModel.searchUser(username)
            }else{
                Toast.makeText(context,getString(R.string.message_empty_username),Toast.LENGTH_LONG).show()
            }
        }

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