package com.githang.rankgithubchina.api

import com.google.gson.annotations.SerializedName

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2018-05-17
 */
data class UserResponse (@SerializedName("users") val users: List<User>)