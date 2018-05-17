/*
 * Copyright (c) 2018. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.githang.rankgithubchina.support

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2018-04-27
 */
object ThreadPools {
    val SINGLE = ThreadPoolExecutor(1, 1,
            0L, TimeUnit.MILLISECONDS,
            LinkedBlockingQueue<Runnable>(), AppThreadFactory())
}