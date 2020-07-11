package com.theappexperts.supergit.ui.commits

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.theappexperts.supergit.R
import com.theappexperts.supergit.models.Commit
import com.theappexperts.supergit.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_commits.*


class CommitsFragment : BaseFragment() {

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

    }

    private fun initAdapter() {
        val COMMITDEMO_1 = Commit(System.currentTimeMillis())
        val COMMITDEMO_2 = Commit(System.currentTimeMillis())
        val COMMITDEMO_3 = Commit(System.currentTimeMillis())
        val COMMITDEMO_4 = Commit(System.currentTimeMillis())
        val COMMITDEMO_5 = Commit(System.currentTimeMillis())
        val COMMITDEMO_6 = Commit(System.currentTimeMillis())
       val  adapterx = CommitsAdapter()
        commits_recyclerView.apply {
            adapter = adapterx
        }
        adapterx.setInitList( listOf(
            COMMITDEMO_1,
            COMMITDEMO_2,
            COMMITDEMO_3,
            COMMITDEMO_4,
            COMMITDEMO_5,
            COMMITDEMO_6
        ))

    }


}