package com.theappexperts.supergit.ui.repo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.theappexperts.supergit.R
import com.theappexperts.supergit.models.GitRepository
import com.theappexperts.supergit.models.GitUser
import com.theappexperts.supergit.ui.BaseFragment
import com.theappexperts.supergit.ui.main.SharedHomeViewModel
import com.theappexperts.supergit.utils.Resource
import kotlinx.android.synthetic.main.fragment_search_repo.*
import org.koin.android.viewmodel.ext.android.viewModel


class SearchRepoFragment : BaseFragment() {
    private val searchRepoViewModel: SearchRepoViewModel by viewModel()
    private val sharedHomeViewModel: SharedHomeViewModel by activityViewModels()
    private val repoItemAdapter by lazy {
        RepoItemAdapter(object : RepoItemAdapter.RepositoryListener{
        override fun onClickItem(user: GitUser) {
            TODO("Not yet implemented")
        }

    })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_repo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repositories_recyclerView.adapter = repoItemAdapter
        subscribeUserSelected()

    }



    private fun subscribeUserSelected() {
        sharedHomeViewModel.userSelectedLiveData.observe(viewLifecycleOwner, Observer { user ->

            user?.let {
                repo_username_textView.text = it.name
                Glide.with(requireContext()).load(it.photo).into(repo_logo_imageview)
                searchRepoViewModel.getPublicRepositoriesByUser(it.name).observe(viewLifecycleOwner,
                    Observer { resource ->
                        when(resource.status){
                            Resource.Status.SUCCESS -> {
                                uiCommunicatorInterface?.hideProgressBar()
                                setRepoAdapter(resource.data!!.peekContent())
                            }
                            Resource.Status.LOADING ->{
                                uiCommunicatorInterface?.showProgressBar()

                            }
                            Resource.Status.ERROR ->{
                                resource.data?.peekContent().let {
                                    setRepoAdapter(resource.data!!.peekContent())
                                }

                                uiCommunicatorInterface?.hideProgressBar()
                            }

                        }
                    })
            } ?: goUserFragment()
        })
    }

    private fun goUserFragment() {
      //  findNavController().navigate(R.id.action_searchRepoFragment_to_navigation_user)
    }

    private fun setRepoAdapter(listRepo:List<GitRepository>){
        repoItemAdapter.list = listRepo
    }
}