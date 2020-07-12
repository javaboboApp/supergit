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

                    if (it.token?.isNotEmpty() == true ){
                    subscribeRepositorySelected(it.name,it.token)
                    }else{
                        subscribeRepositorySelected(it.name)

                    }
                }
            }
        )

    }

    private fun subscribeRepositorySelected(username:String, token: String? = null) {
        sharedHomeViewModel.gitRepoSelectedLiveData.observe(viewLifecycleOwner,
            Observer { repositorySelected ->

                repositorySelected?.let {

                    commitsViewModel.getCommit(username,repositorySelected,token)

                        .observe(
                            viewLifecycleOwner,
                            Observer { resource ->
                                resource.data?.getContentIfNotHandled()?.let { result ->
                                    when (resource.status) {
                                        Resource.Status.SUCCESS -> {
                                            if (result.isEmpty()){
                                                showNoData()
                                            }else
                                                hideNoData()

                                            uiCommunicatorInterface?.hideProgressBar()
                                            setCommitAdapter(result.toMutableList())
                                        }
                                        Resource.Status.LOADING -> {
                                            uiCommunicatorInterface?.showProgressBar()

                                        }
                                        Resource.Status.ERROR -> {
                                            resource.data?.peekContent().let {
                                                setCommitAdapter(result.toMutableList())
                                            }

                                            uiCommunicatorInterface?.hideProgressBar()
                                        }

                                    }
                                }
                            })
                }
            })
    }

    private fun hideNoData() {
        no_data_msg.visibility = View.INVISIBLE
    }

    private fun showNoData() {
        no_data_msg.visibility = View.VISIBLE
    }

    private fun setCommitAdapter(list: MutableList<Commit>) {
        commitsAdapter.list = list
    }
}
