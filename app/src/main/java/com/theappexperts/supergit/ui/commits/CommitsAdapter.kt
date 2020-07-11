package com.theappexperts.supergit.ui.commits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.theappexperts.supergit.R
import com.theappexperts.supergit.models.Commit
import com.theappexperts.supergit.utils.Utitlites.getDateAsHeaderId
import kotlinx.android.synthetic.main.item_adapter_commits.view.*

class CommitsAdapter(var list: List<Commit>) :
    CommitAdapterWrapperHeader(){

    override fun getHeaderId(position: Int): Long = getDateAsHeaderId(list[position].timestamp)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {

           if(viewType ==  VIEW_TYPE.VIEW_HEADER.ordinal){
               val itemView = LayoutInflater.from(parent.context)
                   .inflate(R.layout.item_adapter_commits_header, parent, false)
               return CommitViewHolder(itemView)
           }else{
               //returning the body...
               val itemView = LayoutInflater.from(parent.context)
                   .inflate(R.layout.item_adapter_commits, parent, false)
               return CommitViewHolder(itemView)
           }



    }

//    override fun onViewRecycled(holder: CommitViewHolder) {
//        holder.itemView.findViewById<TextView>(R.id.repo_item_fullname_textview).text = ""
//        super.onViewRecycled(holder)
//    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {

        holder.bind(list[position])
    }



    open inner class CommitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       open fun bind(commit: Commit) = with(itemView) {
          val fullName : TextView = itemView.findViewById(R.id.repo_item_fullname_textview)
           fullName.text = "NORMAL"

        }
    }

    inner class CommitViewHolderHeader(itemView: View) : CommitViewHolder(itemView) {
        override fun bind(commit: Commit) = with(itemView) {
            val fullName : TextView = itemView.findViewById(R.id.repo_item_fullname_textview)
            fullName.text = "HEADER"

        }
    }

}