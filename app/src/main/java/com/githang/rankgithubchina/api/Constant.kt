package com.githang.rankgithubchina.api

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2018-05-17
 */
object Constant {
    const val API_URL = "https://api.github.com/"
    const val AVATAR_URL = "https://avatars3.githubusercontent.com/u/"

    fun getAvatarUrl(userId: String, size: Int): String {
        return "$AVATAR_URL${userId.substringAfter("user-")}?v=4&s=$size"
    }
}