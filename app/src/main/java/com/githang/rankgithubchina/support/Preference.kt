package com.githang.rankgithubchina.support

import android.content.Context
import com.githang.rankgithubchina.RankApplication
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2018-05-17
 */
object Preference {

    private const val FILE_NAME = "preference.conf"

    private const val KEY = "query_time"

    private val DATE_FORMATTER = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    private val preference = lazy {
        RankApplication.INSTANCE.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    fun isTodayQueried(): Boolean {
        return getCurrentDay() == preference.value.getString(KEY, null)
    }

    fun setQueriedToday() {
        preference.value.edit().putString(KEY, getCurrentDay()).apply()
    }

    private fun getCurrentDay(): String {
        return DATE_FORMATTER.format(Calendar.getInstance().timeInMillis)
    }
}