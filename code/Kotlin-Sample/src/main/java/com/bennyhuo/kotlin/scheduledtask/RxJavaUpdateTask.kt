package com.bennyhuo.kotlin.scheduledtask

import com.bennyhuo.kotlin.api.updateApi
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RxJavaUpdateTask : UpdateTask {

    private var disposable: Disposable? = null

    override fun scheduleUpdate(interval: Long) {
        cancel()

        disposable = Observable.interval(interval, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .flatMap {
                updateApi.getConfigObservable()
            }.onErrorResumeNext {
                Observable.just(emptyList())
            }.subscribe {
                println(it)
            }
    }

    override fun cancel() {
        disposable?.dispose()
        disposable = null
    }
}