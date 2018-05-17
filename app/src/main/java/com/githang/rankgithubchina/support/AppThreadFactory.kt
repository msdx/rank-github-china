/*
 * Copyright (c) 2018. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.githang.rankgithubchina.support

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2018-04-27
 */
class AppThreadFactory : ThreadFactory {
        private val mThreadNumber = AtomicInteger(1)
        private val mGroup: ThreadGroup
        private val mNamePrefix: String

        init {
            val s: SecurityManager? = System.getSecurityManager()
            mGroup = s?.threadGroup ?: Thread.currentThread().threadGroup
            mNamePrefix = "app-pool-${POOL_NUMBER.getAndIncrement()}-thread-"
        }

        override fun newThread(r: Runnable): Thread {
            val t = Thread(mGroup, r, mNamePrefix + mThreadNumber.getAndIncrement(), 0)
            if (t.isDaemon) {
                t.isDaemon = false
            }
            if (t.priority != Thread.MIN_PRIORITY) {
                t.priority = Thread.MIN_PRIORITY
            }
            return t
        }

        companion object {
            private val POOL_NUMBER = AtomicInteger(1)
        }
    }