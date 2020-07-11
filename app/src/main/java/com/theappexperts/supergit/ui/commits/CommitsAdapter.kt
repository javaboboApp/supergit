package com.theappexperts.supergit.ui.commits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.theappexperts.supergit.R
import com.theappexperts.supergit.models.Commit
import com.theappexperts.supergit.utils.Utitlites.getDateAsHeaderId

class CommitsAdapter(var list: List<Commit>) :
    CommitAdapterWrapperHeader(){

    override fun getHeaderId(position: Int): Long = getDateAsHeaderId(list[position].timestamp)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {

           if(viewType ==  VIEW_TYPE.VIEW_HEADER.ordinal){
               val mView = LayoutInflater.from(parent.context)
                   .inflate(R.layout.item_adapter_commits_header, parent, false)
               return CommitViewHolder(mView)
           }else{
               //returning the body...
               val mView = LayoutInflater.from(parent.context)
                   .inflate(R.layout.item_adapter_commits, parent, false)
               return CommitViewHolder(mView)
           }



    }



    //    override fun onViewRecycled(holder: CommitViewHolder) {
//        holder.itemView.findViewById<TextView>(R.id.repo_item_fullname_textview).text = ""
//        super.onViewRecycled(holder)
//    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        val itemType = holder.getItemViewType()
        if(itemType == VIEW_TYPE.VIEW_HEADER.ordinal){

            holder.bindHeader(list[position])
        }else{
            holder.bindNormal(list[position])
        }


    }



    open inner class CommitViewHolder(var normalView: View) : RecyclerView.ViewHolder(normalView) {
        fun bindNormal(commit: Commit) = with(normalView) {
          val fullName : TextView = findViewById(R.id.repo_item_fullname_textview)
           fullName.text = "NORMAL"

        }

        fun bindHeader(commit: Commit)= with(normalView) {
            val fullName : TextView = findViewById(R.id.repo_item_fullname_textview)
            fullName.text = "header"

        }

    }



}