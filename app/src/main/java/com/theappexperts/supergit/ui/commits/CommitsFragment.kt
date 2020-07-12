package com.theappexperts.supergit.ui.commits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.theappexperts.supergit.R
import com.theappexperts.supergit.models.Commit
import com.theappexperts.supergit.ui.BaseFragment
import com.theappexperts.supergit.ui.main.SharedHomeViewModel
import com.theappexperts.supergit.utils.Resource
import kotlinx.android.synthetic.main.fragment_commits.*
import org.koin.android.viewmodel.ext.android.viewModel


class CommitsFragment : BaseFragment() {

    private val sharedHomeViewModel: SharedHomeViewModel by activityViewModels()
    private val commitsViewModel: CommitsViewModel by viewModel()
    private val commitsAdapter = CommitsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_commits, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        subscribeUserSelected()

    }

    private fun initAdapter() {
//        val COMMITDEMO_1 = Commit(System.currentTimeMillis())
//        val COMMITDEMO_2 = Commit(System.currentTimeMillis())
//        val COMMITDEMO_3 = Commit(System.currentTimeMillis())
//        val COMMITDEMO_4 = Commit(System.currentTimeMillis())
//        val COMMITDEMO_5 = Commit(System.currentTimeMillis())
//        val COMMITDEMO_6 = Commit(System.currentTimeMillis())
        commits_recyclerView.apply {
            adapter = commitsAdapter
        }
//        adapterx.setInitList(
//            listOf(
//                COMMITDEMO_1,
//                COMMITDEMO_2,
//                COMMITDEMO_3,
//                COMMITDEMO_4,
//                COMMITDEMO_5,
//                COMMITDEMO_6
//            )
//        )

    }


    private fun subscribeUserSelected() {
        sharedHomeViewModel.userSelectedLiveData.observe(
            viewLifecycleOwner,
            Observer { userSelected ->

                userSelected?.let {
                    subscribeRepositorySelected(it.name)
                }
            }
        )

    }

    private fun subscribeRepositorySelected(userName: String) {
        sharedHomeViewModel.gitRepoSelectedLiveData.observe(viewLifecycleOwner,
            Observer { repositorySelected ->

                repositorySelected?.let {
                    commitsViewModel.getCommit(userName, repositorySelected)
                        .observe(
                            viewLifecycleOwner,
                            Observer { resource ->
                                when (resource.status) {
                                    Resource.Status.SUCCESS -> {
                                        uiCommunicatorInterface?.hideProgressBar()
                                        setCommitAdapter(resource.data!!.peekContent())
                                    }
                                    Resource.Status.LOADING -> {
                                        uiCommunicatorInterface?.showProgressBar()

                                    }
                                    Resource.Status.ERROR -> {
                                        resource.data?.peekContent().let {
                                            setCommitAdapter(resource.data!!.peekContent())
                                        }

                                        uiCommunicatorInterface?.hideProgressBar()
                                    }

                                }
                            })
                }
            })
    }

    private fun setCommitAdapter(list: List<Commit>) {
        commitsAdapter.list = list
    }
}
