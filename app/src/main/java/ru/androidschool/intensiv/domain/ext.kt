package ru.androidschool.intensiv.domain

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> Single<T>.subObserve(): Single<T> {
    return  this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun Completable.subObserveComp(): Completable {
    return  this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}