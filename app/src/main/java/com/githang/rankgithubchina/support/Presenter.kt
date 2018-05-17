/*
 * Copyright (c) 2018. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.githang.rankgithubchina.support

import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.lang.ref.WeakReference
import java.util.ArrayList

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @version 2.1
 * @since 2017-03-18
 */
interface Presenter<T : IView> {
    /**
     * Return the t instance
     *
     * @return T instance
     */
    val view: T

    /**
     * unsubscribe all subscribers
     */
    fun unsubscribeAll()

    open class Base<T : IView> protected constructor(override val view: T) : Presenter<T>, SubscriberWatcher {

        private val mSubscriptions = ArrayList<WeakReference<Subscription>>()

        override fun unsubscribeAll() {
            for (ref in mSubscriptions) {
                val subscription = ref.get()
                if (subscription != null && !subscription.isUnsubscribed) {
                    subscription.unsubscribe()
                }
            }
            mSubscriptions.clear()
        }

        private fun recyclerSubscriptions() {
            val iterator = mSubscriptions.iterator()
            while (iterator.hasNext()) {
                val ref = iterator.next()
                if (ref.get()?.isUnsubscribed == true) {
                    iterator.remove()
                }
            }
        }

        protected fun <D> applySchedulers(): Observable.Transformer<D, D> {
            return TRANSFORMER as Observable.Transformer<D, D>
        }

        override fun watchSubscription(subscription: Subscription) {
            recyclerSubscriptions()
            mSubscriptions.add(WeakReference(subscription))
        }

        protected fun cancelSubscription(subscription: Subscription?) {
            if (subscription != null && !subscription.isUnsubscribed) {
                subscription.unsubscribe()
            }
        }

        companion object {
            private val TRANSFORMER = Observable.Transformer<Any, Any> { observable ->
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            }
        }
    }
}
