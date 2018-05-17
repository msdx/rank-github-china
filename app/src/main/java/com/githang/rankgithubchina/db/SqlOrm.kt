/*
 * Copyright (c) 2018. Xi'an iRain IOT Technology service CO., Ltd (ShenZhen). All Rights Reserved.
 */

package com.githang.rankgithubchina.db

import com.githang.rankgithubchina.BuildConfig
import com.githang.rankgithubchina.RankApplication
import com.litesuits.orm.LiteOrm
import com.litesuits.orm.db.assit.QueryBuilder

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @version 2.1
 * @since 2018-04-25 2.0
 */
object SqlOrm {

    private val liteOrm = lazy {
        LiteOrm.newSingleInstance(RankApplication.INSTANCE, "liteorm.db")
                .apply { this.setDebugged(BuildConfig.DEBUG) }
    }

    fun <T> query(cls: Class<T>, limit: Int, orderDesc: String): ArrayList<T> {
        return QueryBuilder(cls).appendOrderDescBy(orderDesc)
                .limit(limit.toString())
                .let { liteOrm.value.query(it) }
    }

    fun <T> saveAll(list: List<T>) {
        liteOrm.value.save(list)
    }
}