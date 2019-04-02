package com.example.githubreposearch.ui.home.presenter

import com.example.githubreposearch.domain.model.GitHubRepositoryModel

interface HomeContract {

    interface View {
        fun setupRecyclerView(list: List<GitHubRepositoryModel>)
        fun openDetailActivity(repository: GitHubRepositoryModel)
        fun showLoadDataError()
        fun showLoadingProgress()
        fun closeProgressDialog()
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun itemClick(repository: GitHubRepositoryModel)
        fun getList(query: String)
    }
}