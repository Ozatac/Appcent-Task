package com.tunahanozatac.appcentapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tunahanozatac.appcentapp.data.api.RetrofitInstance
import com.tunahanozatac.appcentapp.data.model.NewsResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NewsViewModel : ViewModel() {

    var newsList: MutableLiveData<NewsResponse> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        makeApiCall("Türkiye ");
    }

    fun getListObserver(): MutableLiveData<NewsResponse> {
        return newsList
    }

    fun makeApiCall(q: String?) {
        val retrofitInstance = RetrofitInstance.getRetrofit()
        retrofitInstance!!.getAllNews(q)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getNewsListObserverRx())
    }

    private fun getNewsListObserverRx(): Observer<NewsResponse> {
        return object : Observer<NewsResponse> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: NewsResponse) {
                newsList.value = t
                loading.value = true
            }

            override fun onError(e: Throwable) {
                newsList.value = null
                loading.value = false
            }

            override fun onComplete() {

            }

        }
    }
}