/*
 * Copyright (c) 2018. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.githang.rankgithubchina.support

import rx.Subscription

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2018-02-07 1.13
 */
interface SubscriberWatcher {
    /**
     * 观察 subscription
     * @param subscription 订阅者
     */
    fun watchSubscription(subscription: Subscription)
}
