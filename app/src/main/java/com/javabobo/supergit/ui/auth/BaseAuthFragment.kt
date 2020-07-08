package com.javabobo.supergit.ui.auth

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import java.lang.ClassCastException

open class BaseAuthFragment : Fragment() {

    private lateinit var uiComunicatorInterface: CommunicatorsInterface
    private val TAG = "BaseAuthFragment"

    interface CommunicatorsInterface {
        fun showProgressBar()
        fun hideProgressBar()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            context as CommunicatorsInterface
        } catch (exception: ClassCastException) {
            Log.i(TAG, "$context must implement CommunicatorsInterface ")
        }
    }
}