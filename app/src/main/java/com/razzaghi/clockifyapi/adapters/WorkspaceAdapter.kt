package com.razzaghi.clockifyapi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.razzaghi.clockifyapi.R
import com.razzaghi.clockifyapi.model.Workspace
import kotlinx.android.synthetic.main.item_workspace_preview.view.*

class WorkspaceAdapter:RecyclerView.Adapter<WorkspaceAdapter.WorkspaceViewHolder>() {

    inner class WorkspaceViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Workspace>(){
        override fun areItemsTheSame(oldItem: Workspace, newItem: Workspace): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Workspace, newItem: Workspace): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkspaceViewHolder {
        return  WorkspaceViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_workspace_preview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: WorkspaceViewHolder, position: Int) {
        val workspace = differ.currentList[position]
        holder.itemView.apply {
            Glide
                .with(this)
                .load(workspace.imageUrl)
                .placeholder(R.drawable.ic_baseline_image_24_glide_loader2)
                .error(R.drawable.ic_baseline_image_24_glide_loader2)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .centerCrop()
                //.fitCenter()
                .into(imgWorkspace)

            val newName = workspace.name.split("'s workspace")
            txtWorkspace.text= newName[0]

        }
    }



}