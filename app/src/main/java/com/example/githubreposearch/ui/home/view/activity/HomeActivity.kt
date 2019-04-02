package com.example.githubreposearch.ui.home.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.githubreposearch.MainApplication
import com.example.githubreposearch.R
import com.example.githubreposearch.domain.model.GitHubRepositoryModel
import com.example.githubreposearch.ui.home.presenter.HomeContract
import com.example.githubreposearch.ui.home.view.adapter.GitHubRepoAdapter
import com.example.githubreposearch.ui.home.view.fragment.DetailFragment
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HomeContract.View {

    @Inject
    lateinit var presenter: HomeContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MainApplication).appComponent.inject(this)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        presenter.attach(this)
    }

    override fun setupRecyclerView(list: List<GitHubRepositoryModel>) {
        val adapter = GitHubRepoAdapter(list)
        adapter.onClickListener = View.OnClickListener { v ->
            presenter.itemClick(v.tag as GitHubRepositoryModel)
        }
        item_list.adapter = adapter
    }

    override fun openDetailActivity(repository: GitHubRepositoryModel) {
        startActivity(intentFor<DetailActivity>(DetailFragment.ARG_ITEM to repository))
    }

    override fun showLoadDataError() {
        toast(getString(R.string.load_data_error))
    }
}
