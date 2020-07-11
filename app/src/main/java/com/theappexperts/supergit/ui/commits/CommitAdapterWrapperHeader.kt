package com.theappexperts.supergit.ui.commits

import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchUIUtil
import androidx.recyclerview.widget.RecyclerView

open abstract class CommitAdapterWrapperHeader :  RecyclerView.Adapter<CommitsAdapter.CommitViewHolder>()   {
    enum class VIEW_TYPE {
        VIEW_HEADER, VIEW_BODY
    }

    private val _cacheHeader = HashMap<Long, Boolean>()

    override fun getItemViewType(position: Int): Int {
        val headerId = getHeaderId(position)

       if(_cacheHeader[headerId] == null){
           _cacheHeader[headerId] = true
           return VIEW_TYPE.VIEW_HEADER.ordinal
       }
        return VIEW_TYPE.VIEW_BODY.ordinal
    }

    abstract fun getHeaderId(position: Int) : Long

}