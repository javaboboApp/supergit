package com.theappexperts.supergit.ui.repo

import com.theappexperts.supergit.models.GitUser
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.theappexperts.supergit.R
import com.theappexperts.supergit.models.GitRepository
import kotlinx.android.synthetic.main.item_adapter_repo.view.*

class RepoItemAdapter(val listener: RepositoryListener) :
    RecyclerView.Adapter<RepoItemAdapter.ItemViewHolder>() {
    var list: List<GitRepository> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_adapter_repo, parent, false)
        return ItemViewHolder(
            view
        )
    }


    override fun getItemCount(): Int {
        return list.size
    }

    interface RepositoryListener {
        fun onClickItem(user: GitUser)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(gitRepository: GitRepository, listener: RepositoryListener) = with(itemView) {
            gitRepository.apply {
                repo_item_fullname_textview.text = this.full_name
                repo_item_description.text = this.description
            }

        }
    }

}