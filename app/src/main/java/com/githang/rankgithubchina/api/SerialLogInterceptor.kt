/*
 * Copyright (c) 2018. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.githang.rankgithubchina.api

import com.githang.rankgithubchina.support.ThreadPools
import com.parkingwang.okhttp3.LogInterceptor.LogInterceptor
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.platform.Platform

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2018-04-26 2.0
 */
class SerialLogInterceptor : LogInterceptor(SerialLogger()) {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return super.intercept(chain)
        } finally {
            flushLog()
        }
    }

    private fun flushLog() {
        val list = MESSAGES.get()
        ThreadPools.SINGLE.execute {
            list.forEach { msg -> Platform.get().log(Platform.WARN, msg, null) }
        }
        MESSAGES.set(ArrayList(INITIAL_CAPACITY))
    }

    companion object {
        private const val INITIAL_CAPACITY = 17
        private val MESSAGES = ThreadStringList()
    }

    class SerialLogger : LogInterceptor.Logger {
        override fun log(message: String?) {
            MESSAGES.get().add(message ?: return)
        }
    }

    class ThreadStringList : ThreadLocal<MutableList<String>>() {
        override fun initialValue(): MutableList<String> {
            return ArrayList(INITIAL_CAPACITY)
        }
    }

}