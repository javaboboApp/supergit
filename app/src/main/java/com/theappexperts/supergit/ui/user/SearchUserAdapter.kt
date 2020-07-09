package com.theappexperts.supergit.ui.user

import com.theappexperts.supergit.models.GitUser
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.theappexperts.supergit.R
import kotlinx.android.synthetic.main.item_search_user.view.*

class SearchUserAdapter (private val listener: SearchUserListener): RecyclerView.Adapter<SearchUserAdapter.ItemViewHolder>() {

    var list: List<GitUser> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_user, parent, false)
        return ItemViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface SearchUserListener {
        fun onClickItem(user: GitUser)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(user = list[position], listener = listener)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(user: GitUser, listener: SearchUserListener) = with(itemView) {
            search_bar_username_textView.text = user.name
            Glide.with(context).load(user.photo).into(search_bar_imageView)
            setOnClickListener { listener.onClickItem(user) }
        }
    }


}