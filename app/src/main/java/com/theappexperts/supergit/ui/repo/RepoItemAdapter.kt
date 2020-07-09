package com.theappexperts.supergit.ui.repo

import com.theappexperts.supergit.models.GitUser
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.theappexperts.supergit.R

class RepoItemAdapter(val listener: Listener) :
    RecyclerView.Adapter<RepoItemAdapter.ItemViewHolder>() {
    var list: List<GitUser> = listOf()
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

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    fun remove(position: Int) {
        listener.remove(list[position],position)
    }

    fun removeAt(position: Int){
        list.toMutableList().removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface Listener {
        fun remove(user: GitUser, position: Int)
        fun onClickItem(user: GitUser)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(user = list[position], listener = listener)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: GitUser, listener: Listener) = with(itemView) {
//            title_textview.text =post.title
//            create_date_textview.text = getDate(post.timestamp)
//            setOnClickListener {
//                listener.onClickItem(post)
//            }
//            share_imageview.setOnClickListener {
//                listener.onClickShareButtom(post)
//            }
//            Glide.with(context)
//                .into(image_imageview)

        }
    }

}