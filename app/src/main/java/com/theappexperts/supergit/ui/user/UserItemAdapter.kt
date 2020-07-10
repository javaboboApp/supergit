package com.theappexperts.supergit.ui.user

import com.theappexperts.supergit.models.GitUser
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.theappexperts.supergit.R
import kotlinx.android.synthetic.main.item_adapter_user.view.*

class UserItemAdapter(val listener: UserItemsListener) :
    RecyclerView.Adapter<UserItemAdapter.ItemViewHolder>() {
    var list: List<GitUser> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_adapter_user, parent, false)
        return ItemViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface UserItemsListener {
        fun onClickItem(user: GitUser)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(user = list[position], listener = listener)
    }
    fun removeUser(itemPosition: Int) {
        notifyItemRemoved(itemPosition)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: GitUser, listener: UserItemsListener) = with(itemView) {

            username_textview.text= user.name
            Glide.with(context).load(user.photo)
                .into(logo_imageview)
            setOnClickListener {
                listener.onClickItem(user)
            }

        }
    }

}