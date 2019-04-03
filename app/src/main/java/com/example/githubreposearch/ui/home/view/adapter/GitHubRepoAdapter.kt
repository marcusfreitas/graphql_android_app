package com.example.githubreposearch.ui.home.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.githubreposearch.R
import com.example.githubreposearch.domain.model.GitHubRepositoryModel
import kotlinx.android.synthetic.main.item_list_content.view.*

class GitHubRepoAdapter(private val values: List<GitHubRepositoryModel>) :
    RecyclerView.Adapter<GitHubRepoAdapter.ViewHolder>() {

    var onClickListener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.repoName.text = item.name
        holder.repoDescription.text = item.description
        holder.forkCount.text = item.forksCount.toString()

        Glide.with(holder.holderView)
            .load(item.avatarUrl)
            .into(holder.userAvatar)

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val holderView = view
        val repoName: TextView = view.repo_name
        val repoDescription: TextView = view.repo_description
        val forkCount: TextView = view.repo_fork_count
        val userAvatar: ImageView = view.user_avatar
    }
}