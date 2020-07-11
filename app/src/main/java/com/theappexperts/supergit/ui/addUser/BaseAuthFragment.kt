package com.theappexperts.supergit.ui.addUser

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import java.lang.ClassCastException

open class BaseAuthFragment : Fragment() {

    lateinit
    var uiCommunicatorInterface: CommunicatorsInterface
    private val TAG = "BaseAuthFragment"

    interface CommunicatorsInterface {
        fun showProgressBar()
        fun hideProgressBar()
        fun showGithubLogin()
        fun navigateToGraph(navGraphId: Int)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            context as CommunicatorsInterface
            uiCommunicatorInterface = context
        } catch (exception: ClassCastException) {
            Log.i(TAG, "$context must implement CommunicatorsInterface ")
        }
    }
}