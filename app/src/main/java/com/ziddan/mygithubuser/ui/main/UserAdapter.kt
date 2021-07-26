package com.ziddan.mygithubuser.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ziddan.mygithubuser.data.general.UserItem
import com.ziddan.mygithubuser.databinding.ItemListUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    private val mData = ArrayList<UserItem>()

    fun setData(items: ArrayList<UserItem>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userItem: UserItem) {
            val imageWidth = 70
            val imageHeight = 70

            Glide.with(itemView)
                .load(userItem.avatar_url)
                .apply(RequestOptions().override(imageWidth, imageHeight))
                .into(binding.ivAvatar)

            binding.tvUsername.text = userItem.login

            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(userItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val mView = ItemListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(mView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserItem)
    }
}