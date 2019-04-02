package com.example.githubreposearch.ui.home.presenter

import com.example.githubreposearch.domain.model.GitHubRepositoryModel
import com.example.githubreposearch.domain.usecase.GitHubReposUseCase
import com.example.githubreposearch.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class HomePresenter(
    private val useCase: GitHubReposUseCase,
    private val scheduler: BaseSchedulerProvider
) : HomeContract.Presenter {

    var view: HomeContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    override fun attach(view: HomeContract.View) {
        this.view = view
    }

    override fun detach() {
        compositeDisposable.clear()
    }

    override fun getList(query: String) {
        compositeDisposable.add(useCase.get(query)
            .observeOn(scheduler.ui())
            .subscribeOn(scheduler.io())
            .subscribe { list: List<GitHubRepositoryModel>?, throwable: Throwable? ->
                if (throwable == null && list != null) {
                    view?.setupRecyclerView(list)
                } else {
                    Timber.e(throwable)
                    view?.showLoadDataError()
                }
            })
    }

    override fun itemClick(repository: GitHubRepositoryModel) {
        view?.openDetailActivity(repository)
    }

}