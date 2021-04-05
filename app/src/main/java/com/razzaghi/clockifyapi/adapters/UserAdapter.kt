package com.razzaghi.clockifyapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.razzaghi.clockifyapi.R
import com.razzaghi.clockifyapi.model.User
import com.razzaghi.clockifyapi.model.Workspace
import kotlinx.android.synthetic.main.item_user_preview.view.*
import okhttp3.ResponseBody

class UserPhotoAdapter(
    private val ClickListener: (User) -> Unit
) :
    PagingDataAdapter<User, UserPhotoAdapter.UserViewHolder>(USER_COMPARATOR) {


    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPhotoAdapter.UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user_preview,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val user = getItem(position)
        val currentItem = getItem(position)

        holder.itemView.apply {
                Glide
                    .with(this)
                    .load(user!!.profilePicture)
                    .placeholder(R.drawable.ic_baseline_image_24_glide_loader2)
                    .error(R.drawable.ic_baseline_image_24_glide_loader2)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(true)
                    .centerCrop()
                    //.fitCenter()
                    .into(imgUser)


            setOnClickListener {
                ClickListener(user)
            }
                txtUserName.text =user.name
                txtUserEmail.text =user.email

        }
    }

   /* inner class UserViewHolder(private val binding: ItemUnsplashPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(photo: UnsplashPhoto) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)

                textViewUserName.text = photo.user.username
            }
        }
    }
*/
    interface OnItemClickListener {
        fun onItemClick(user: User)
    }

    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User) =
                oldItem.id  == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User) =
                oldItem == newItem
        }
    }


}