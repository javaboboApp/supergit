package com.theappexperts.supergit.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.theappexperts.supergit.R
import com.theappexperts.supergit.models.GitUser
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
        initUserAdapter()
        subscribeGetCurrentUser()

        no_user_button.setOnClickListener {
            //navigate to another graph
            uiCommunicatorInterface?.navigateToGraph(R.id.add)
        }
        initOnTouchListener()


    }


    private fun initUserAdapter() {
        users_recycler.adapter = currentUserItemAdapter
    }


    private fun subscribeGetCurrentUser() {
        userViewModel.getCurrentUsers().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                SUCCESS -> {
                    it.data?.peekContent()?.let {
                        if (it.isEmpty()) {
                            showUserEmptyLayout()
                        }else
                            hideNotUserLayout()

                        setCurrentUsers(it)
                    }
                    uiCommunicatorInterface?.hideProgressBar()

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

    private fun showUserEmptyLayout() {
        no_user_layout.visibility = View.VISIBLE

    }

    private fun hideNotUserLayout() {
        no_user_layout.visibility = View.GONE
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
                override fun onSwipedUser(
                    user: GitUser,
                    itemPosition: Int
                ) {
                    userViewModel.removeUser(user).observe(viewLifecycleOwner, Observer { result ->
                        when (result.status) {
                            SUCCESS -> {
                                currentUserItemAdapter.notifyItemChanged(itemPosition)
                            }
                            ERROR -> {
                                showMsgRemoving()
                            }
                        }


                    }
                    )
                }

            })

    private fun showMsgRemoving() {
        Toast.makeText(
            requireContext(),
            getString(R.string.error_removing_user_msg),
            Toast.LENGTH_LONG
        ).show()
    }


    fun goSearchRepoFragment() {

        try {

            findNavController().navigate(R.id.action_user_to_searchRepoFragment)
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "Multiple navigation attempts handled. ", e)
        }
    }


    override fun onClickItem(user: GitUser) {
        sharedHomeViewModel.selectUser(user)
        goSearchRepoFragment()
    }


}