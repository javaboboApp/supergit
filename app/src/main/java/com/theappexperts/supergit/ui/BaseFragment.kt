package com.theappexperts.supergit.ui

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.theappexperts.supergit.ui.auth.BaseAuthFragment
import java.lang.ClassCastException

private const val TAG = "BaseFragment"
open class BaseFragment : Fragment(){

     var uiCommunicatorInterface: CommunicatorsInterface? =null


    interface CommunicatorsInterface {
        fun showProgressBar()
        fun hideProgressBar()

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