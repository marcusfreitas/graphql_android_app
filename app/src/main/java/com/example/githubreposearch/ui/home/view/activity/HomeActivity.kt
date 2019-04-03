package com.example.githubreposearch.ui.home.view.activity

import android.app.ProgressDialog
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.githubreposearch.MainApplication
import com.example.githubreposearch.R
import com.example.githubreposearch.domain.model.GitHubRepositoryModel
import com.example.githubreposearch.ui.home.presenter.HomeContract
import com.example.githubreposearch.ui.home.view.adapter.GitHubRepoAdapter
import com.example.githubreposearch.ui.home.view.fragment.DetailFragment
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), HomeContract.View {

    @Inject
    lateinit var presenter: HomeContract.Presenter

    private var recyclerView: RecyclerView? = null
    private var progressDialog: ProgressDialog? = null
    private var savedList: List<GitHubRepositoryModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MainApplication).appComponent.inject(this)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        recyclerView = item_list

        presenter.attach(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.detach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Integer.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    presenter.getList(query)
                }
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.action_search) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setupRecyclerView(list: List<GitHubRepositoryModel>) {
        savedList = list
        val adapter = GitHubRepoAdapter(list)
        adapter.onClickListener = View.OnClickListener { v ->
            presenter.itemClick(v.tag as GitHubRepositoryModel)
        }
        recyclerView?.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun openDetailActivity(repository: GitHubRepositoryModel) {
        startActivity(intentFor<DetailActivity>(DetailFragment.ARG_ITEM to repository))
    }

    override fun showLoadingProgress() {
        progressDialog = indeterminateProgressDialog("Please wait a bit...", "Fetching data")
        progressDialog?.show()
    }

    override fun closeProgressDialog() {
        progressDialog?.dismiss()
    }

    override fun showLoadDataError() {
        toast(getString(R.string.load_data_error))
    }
}
