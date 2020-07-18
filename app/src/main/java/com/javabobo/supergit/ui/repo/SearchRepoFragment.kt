package com.javabobo.supergit.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.javabobo.supergit.R
import com.javabobo.supergit.models.GitRepository
import com.javabobo.supergit.models.GitUser
import com.javabobo.supergit.ui.BaseFragment
import com.javabobo.supergit.ui.main.SharedHomeViewModel
import com.javabobo.supergit.utils.Resource
import kotlinx.android.synthetic.main.fragment_search_repo.*
import org.koin.android.viewmodel.ext.android.viewModel


class SearchRepoFragment : BaseFragment() {
    private val searchRepoViewModel: SearchRepoViewModel by viewModel()
    private val sharedHomeViewModel: SharedHomeViewModel by activityViewModels()
    private val repoItemAdapter by lazy {
        RepoItemAdapter(object : RepoItemAdapter.RepositoryListener {
            override fun onClickItem(gitRepository: GitRepository) {
                sharedHomeViewModel.selectRepository(gitRepository)
                findNavController().navigate(R.id.action_searchRepoFragment_to_commitsFragment)
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
                //get public or get everything(user private)...

                if (it.token?.isNotEmpty() == true) {

                    getAllRepositories(it.name, it.token!!)
                } else {
                    getPublicRepositories(it)
                }


            } ?: goUserFragment()
        })
    }

    private fun getAllRepositories(userName: String, token: String) {
        searchRepoViewModel.getPrivateAndPublicRepositories(userName, token)
            .observe(viewLifecycleOwner,
                Observer { resource ->
                    resource.data?.getContentIfNotHandled()?.let {
                        when (resource.status) {
                            Resource.Status.SUCCESS -> {
                                uiCommunicatorInterface?.hideProgressBar()
                                setRepoAdapter(resource.data!!.peekContent())
                            }
                            Resource.Status.LOADING -> {
                                uiCommunicatorInterface?.showProgressBar()

                            }
                            Resource.Status.ERROR -> {
                                resource.data?.peekContent().let {
                                    setRepoAdapter(resource.data!!.peekContent())
                                }
                                showError()
                                uiCommunicatorInterface?.hideProgressBar()
                            }

                        }
                    }
                })

    }

    private fun showError() {
        Toast.makeText(requireContext(), "Something was wrong :(", Toast.LENGTH_LONG).show()
    }

    private fun getPublicRepositories(it: GitUser) {
        searchRepoViewModel.getPublicRepositoriesByUser(it.name).observe(viewLifecycleOwner,
            Observer { resource ->
                resource.data?.getContentIfNotHandled()?.let {

                    when (resource.status) {
                        Resource.Status.SUCCESS -> {
                            uiCommunicatorInterface?.hideProgressBar()
                            setRepoAdapter(resource.data!!.peekContent())
                        }
                        Resource.Status.LOADING -> {
                            uiCommunicatorInterface?.showProgressBar()

                        }
                        Resource.Status.ERROR -> {
                            resource.data?.peekContent().let {
                                setRepoAdapter(resource.data!!.peekContent())
                            }
                            uiCommunicatorInterface?.hideProgressBar()
                        }
                    }
                }
            })
    }

    private fun goUserFragment() {
        //  findNavController().navigate(R.id.action_searchRepoFragment_to_navigation_user)
    }

    private fun setRepoAdapter(listRepo: List<GitRepository>) {
        repoItemAdapter.list = listRepo
    }
}