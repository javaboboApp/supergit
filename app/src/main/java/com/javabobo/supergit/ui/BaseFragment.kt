package com.javabobo.supergit.ui

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import java.lang.ClassCastException

private const val TAG = "BaseFragment"

open class BaseFragment : Fragment() {

    var uiCommunicatorInterface: CommunicatorsInterface? = null
    val firebaseAuth: FirebaseAuth by inject()

    interface CommunicatorsInterface {
        fun showProgressBar()
        fun hideProgressBar()
        fun navigateToGraph(navGraphId: Int)
        fun hideKeyBoard()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
            try {

                uiCommunicatorInterface = provideComunicatorInterface(context)
            } catch (exception: ClassCastException) {
                Log.i(TAG, "$context must implement CommunicatorsInterface ")
            }
    }

    fun provideComunicatorInterface(context: Context) = context as CommunicatorsInterface

}