package com.hao.mvp.di

import androidx.lifecycle.LifecycleCoroutineScope
import com.hao.mvp.counter.CounterPresenter
import com.hao.mvp.counter.ICounterPresenter
import com.hao.mvp.counter.ICounterView
import org.koin.dsl.module

val appModule = module {
    // Presenter factory with parameters
    factory<ICounterPresenter> { (view: ICounterView, scope: LifecycleCoroutineScope) ->
        CounterPresenter(view, scope)
    }
}
