/*
 * Copyright (c) 2018. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.githang.rankgithubchina.support

import rx.Subscription
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

    open class Base<T : IView> protected constructor(override val view: T) : Presenter<T> {

        private val mSubscriptions = ArrayList<WeakReference<Subscription>>()

        protected fun watchSubscription(subscription: Subscription) {
            recyclerSubscriptions()
            mSubscriptions.add(WeakReference(subscription))
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

        override fun unsubscribeAll() {
            mSubscriptions.forEach { cancelSubscription(it.get()) }
            mSubscriptions.clear()
        }

        private fun cancelSubscription(subscription: Subscription?) {
            if (subscription != null && !subscription.isUnsubscribed) {
                subscription.unsubscribe()
            }
        }
    }
}
