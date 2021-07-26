package com.ziddan.consumerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ziddan.consumerapp.databinding.ItemListUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

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
}