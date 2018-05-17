package com.githang.rankgithubchina.main

import com.githang.rankgithubchina.api.User
import com.githang.rankgithubchina.support.IView

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2018-05-17
 */
interface UserView : IView {

    fun showLoading()

    fun dismissLoading()

    fun showUsers(users: List<User>)
}