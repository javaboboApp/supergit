package com.appexperts.supergit.ui.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.appexperts.supergit.R
import com.appexperts.supergit.models.User

class UserItemAdapter(val listener: Listener) :
    RecyclerView.Adapter<UserItemAdapter.ItemViewHolder>() {
    var list: List<User> = listOf()
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
        fun remove(user: User, position: Int)
        fun onClickItem(user: User)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(user = list[position], listener = listener)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User, listener: Listener) = with(itemView) {
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