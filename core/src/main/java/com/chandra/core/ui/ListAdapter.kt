package com.chandra.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chandra.core.databinding.ItemRowBinding


import com.chandra.core.domain.model.Users


class ListAdapter(private val listUsers: ArrayList<Users>) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    fun setData(items: List<Users>?) {
        listUsers.apply {
            clear()
            items?.let { addAll(it) }
        }
        notifyDataSetChanged()
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    class ListViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {

        return ListViewHolder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUsers[position]
        holder.binding.apply {
            tvName.text = user.login

            Glide.with(holder.itemView.context).load(user.avatarUrl)
                .apply(RequestOptions().override(50, 50)).into(civImage)
            root.setOnClickListener { onItemClickCallback?.onItemClicked(listUsers[position]) }

        }


    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Users)
    }


}

