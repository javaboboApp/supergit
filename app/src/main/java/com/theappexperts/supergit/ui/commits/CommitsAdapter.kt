package com.theappexperts.supergit.ui.commits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.theappexperts.supergit.R
import com.theappexperts.supergit.models.Commit
import com.theappexperts.supergit.utils.DateUtils.getDate
import com.theappexperts.supergit.utils.DateUtils.getDateAsHeaderId
import kotlinx.android.synthetic.main.item_adapter_commits.view.*
import kotlinx.android.synthetic.main.item_adapter_commits_header.view.*

class CommitsAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list: List<Commit> = listOf()
        set(value) {
            field = setInitList(value)
            notifyDataSetChanged()
        }


    fun setInitList(field: List<Commit>): List<Commit> {
        val _cacheHeader = HashMap<Long, Boolean>()
        field.forEach { commit ->
            val headerId = getDateAsHeaderId(commit.timestamp)
            if (_cacheHeader[headerId] == null) {
                _cacheHeader[headerId] = true
                commit.isHeader = true
            }
        }

        return field

    }

    override fun getItemViewType(position: Int): Int {
        if (list[position].isHeader) {
            return 0
        }
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 0) {
            val mView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_adapter_commits_header, parent, false)
            return CommitViewHolder(mView)
        } else {
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


    override fun getItemCount(): Int {
        val n = list.size
        return n
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemType = holder.getItemViewType()
        holder as CommitViewHolder
        if (itemType == 0) {

            holder.bindHeader(list[position])
        } else {
            holder.bindNormal(list[position])
        }
    }

    //CommiViewHolder
    inner class CommitViewHolder(var normalView: View) : RecyclerView.ViewHolder(normalView) {
        fun bindNormal(commit: Commit) = with(normalView) {
            commit.apply {
                commit_item_message_textview.text = this.message
                commit_item_author.text = this.authorName
                commit_item_date.text = context.getString(
                    R.string.commit_item_date_text,
                    getDate(this.timestamp)
                )
            }
        }

        fun bindHeader(commit: Commit) = with(normalView) {
            val fullName: TextView = findViewById(R.id.header_date_textview)
            fullName.text = "header"
            header_date_textview.text = getDate(commit.timestamp)

        }

    }
}



