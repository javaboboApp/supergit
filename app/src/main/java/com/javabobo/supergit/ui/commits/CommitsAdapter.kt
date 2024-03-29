package com.javabobo.supergit.ui.commits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javabobo.supergit.R
import com.javabobo.supergit.models.Commit
import com.javabobo.supergit.utils.DateUtils.getDate
import com.javabobo.supergit.utils.DateUtils.getDateAsHeaderId
import kotlinx.android.synthetic.main.item_adapter_commits.view.*
import kotlinx.android.synthetic.main.item_adapter_commits_header.view.*

class CommitsAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list: MutableList<Commit> = mutableListOf()
        set(value) {
            field = setInitList(value)
            notifyDataSetChanged()
        }


    fun setInitList(field: MutableList<Commit>): MutableList<Commit> {

        val _cacheHeader = HashMap<Long, Boolean>()
        val iterator = field.listIterator()

        while (iterator.hasNext()) {
            val commit = iterator.next()

            val headerId = getDateAsHeaderId(commit.timestamp)
            if (_cacheHeader[headerId] == null) {
                _cacheHeader[headerId] = true
                commit.isHeader = true

                iterator.add(
                    Commit(
                        timestamp = commit.timestamp,
                        isHeader = false,
                        repoId = commit.repoId,
                        authorName = commit.authorName,
                        message = commit.message
                    )
                )
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
            header_date_textview.text = getDate(commit.timestamp)

        }

    }
}



