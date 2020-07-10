package com.theappexperts.supergit.ui.repo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.theappexperts.supergit.R
import com.theappexperts.supergit.ui.main.SharedHomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class SearchRepoFragment : Fragment() {
    private val searchRepoViewModel: SearchRepoViewModel by viewModel()
    private val sharedHomeViewModel: SharedHomeViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_repo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedHomeViewModel.userSelectedLiveData.observe(requireActivity(), Observer { user ->

            user?.let {
                searchRepoViewModel.getPublicRepositoriesByUser(it.name)
            } ?: goUserFragment()
        })

    }

    private fun goUserFragment() {
      //  findNavController().navigate(R.id.action_searchRepoFragment_to_navigation_user)
    }
}