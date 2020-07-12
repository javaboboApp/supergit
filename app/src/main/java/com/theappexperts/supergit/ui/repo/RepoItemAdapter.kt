package com.theappexperts.supergit.ui.repo

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
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
        fun onClickItem(gitRepository: GitRepository)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun onViewRecycled(holder: ItemViewHolder) {
        super.onViewRecycled(holder)
        holder.itemView.findViewById<TextView>(R.id.repo_item_visibility_textView).text = null
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(gitRepository: GitRepository, listener: RepositoryListener) = with(itemView) {
            gitRepository.apply {
                repo_item_fullname_textview.text = full_name
                repo_item_description.text = description
                repo_item_visibility_textView.apply {
                    when (private) {
                        true -> {
                            text = context.getString(R.string.visibility_private)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                setTextColor(context.getColor(R.color.visibility_private_text_color))
                            else
                                setTextColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.visibility_private_text_color
                                    )
                                )
                        }
                        else -> {
                            text = context.getString(R.string.visibility_public)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                setTextColor(context.getColor(R.color.visibility_public_text_color))
                            else
                                setTextColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.visibility_public_text_color
                                    )
                                )
                        }
                    }
                }

                setOnClickListener { listener.onClickItem(gitRepository) }

            }
        }

    }
}