package com.githang.rankgithubchina.main

import com.githang.rankgithubchina.api.ApiService
import com.githang.rankgithubchina.api.User
import com.githang.rankgithubchina.api.UserService
import com.githang.rankgithubchina.db.SqlOrm
import com.githang.rankgithubchina.support.Preference
import com.githang.rankgithubchina.support.Presenter
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.CountDownLatch

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2018-05-17
 */
interface UserPresenter : Presenter<UserView> {

    fun queryFromGithub()

    fun queryFromDatabase()

    class Impl(view: UserView) : Presenter.Base<UserView>(view), UserPresenter {
        private lateinit var mRequestCounter: CountDownLatch

        override fun queryFromGithub() {
            view.showLoading()
            val pageSize = 10
            mRequestCounter = CountDownLatch(pageSize * 2)
            val service = ApiService.create(UserService::class.java)
            for (i in 1..pageSize) {
                queryUsers(service, "china", i)
                queryUsers(service, "中国", i)
            }
        }

        private fun queryUsers(service: UserService, location: String, startPage: Int) {
            service.users(location, startPage)
                    .subscribeOn(Schedulers.io())
                    .doOnNext { SqlOrm.saveAll(it.users) }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ countDownRequest() }, { countDownRequest() })
                    .let { watchSubscription(it) }
        }

        private fun countDownRequest() {
            mRequestCounter.countDown()
            if (mRequestCounter.count == 0L) {
                Preference.setQueriedToday()
                queryFromDatabase()
            }
        }

        override fun queryFromDatabase() {
            Observable.just(User::class.java)
                    .doOnSubscribe({ view.showLoading() })
                    .subscribeOn(Schedulers.io())
                    .map { SqlOrm.query(it, 1000, User.COL_FOLLOWERS) }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ view.showUsers(it) }, { view.dismissLoading() }, { view.dismissLoading() })
        }
    }
}