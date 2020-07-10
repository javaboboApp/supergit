package com.theappexperts.supergit.ui.user

import com.theappexperts.supergit.models.GitUser
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.theappexperts.supergit.R
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.theappexperts.supergit.ui.BaseFragment
import com.theappexperts.supergit.ui.main.SharedHomeViewModel
import com.theappexperts.supergit.utils.Resource.Status.*
import kotlinx.android.synthetic.main.fragment_user.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TAG = "UserFragment"

class UserFragment : BaseFragment(), UserItemAdapter.UserItemsListener {

    private val currentUserItemAdapter: UserItemAdapter = UserItemAdapter(this)
    private val userViewModel: UserViewModel by viewModel()
    private val sharedHomeViewModel: SharedHomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUserSelected()
        initUserAdapter()
        subscribeGetCurrentUser()
        subscribeIsShowingNoUserLayout()
        no_user_button.setOnClickListener { findNavController().navigate(R.id.action_navigation_user_to_navigation_add) }
        initOnTouchListener()


    }

    private fun subscribeUserSelected() {
        sharedHomeViewModel.userSelectedLiveData.observe(requireActivity(), Observer { user ->
            user?.let {goSearchRepoFragment()  }
        })
    }


    private fun subscribeIsShowingNoUserLayout() {
        userViewModel.showNoUserLayout.observe(requireActivity(), Observer { it ->
            no_user_layout.visibility = when (it) {
                true -> View.VISIBLE
                else -> View.GONE
            }

        })
    }

    private fun initUserAdapter() {
        users_recycler.adapter = currentUserItemAdapter
    }


    private fun subscribeGetCurrentUser() {
        userViewModel.getCurrentUsers().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                SUCCESS -> {
                    uiCommunicatorInterface?.hideProgressBar()
                    setCurrentUsers(it.data?.peekContent()!!)

                }
                ERROR -> {
                    uiCommunicatorInterface?.hideProgressBar()
                    showErrorMsg()
                }

                LOADING -> {
                    uiCommunicatorInterface?.showProgressBar()
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


    private fun setCurrentUsers(users: List<GitUser>) {
        currentUserItemAdapter.list = users
    }



    private fun initOnTouchListener() {
        val itemTouchHelper: ItemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(users_recycler)
    }

    val callback: ItemTouchHelper.SimpleCallback =
        CustomItemTouchHelper(
            currentUserItemAdapter,
            object :
                CustomItemTouchHelper.CustomSwipListner {
                override fun onSwipedUser(user: GitUser) {
                    userViewModel.removeUser(user).observe(viewLifecycleOwner, Observer { result ->

                        if (result.status == ERROR) {
                            Toast.makeText(
                                requireContext(),
                                "Error Occurred during suppression",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                    )
                }

            })


    fun goSearchRepoFragment(){
        findNavController().navigate(R.id.action_navigation_user_to_searchRepoFragment)
    }


    override fun onClickItem(user: GitUser) {
        sharedHomeViewModel.selectUser(user)
        goSearchRepoFragment()
    }


}