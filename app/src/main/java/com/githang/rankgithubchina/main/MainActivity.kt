package com.githang.rankgithubchina.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.recyclerview.extensions.AsyncDifferConfig
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.githang.rankgithubchina.R
import com.githang.rankgithubchina.api.User
import com.githang.rankgithubchina.support.Preference
import kotlinx.android.synthetic.main.activity_main.progress_bar
import kotlinx.android.synthetic.main.activity_main.recycler_view

class MainActivity : AppCompatActivity(), UserView {

    private val mPresenter = UserPresenter.Impl(this)
    private lateinit var mAdapter: ListAdapter<User, UserViewHolder>
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mLayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = mLayoutManager
        val inflater = LayoutInflater.from(this)
        mAdapter = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return TextUtils.equals(oldItem.id, newItem.id)
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
                .let { AsyncDifferConfig.Builder<User>(it).build() }
                .let {
                    object : ListAdapter<User, UserViewHolder>(it) {
                        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
                            return UserViewHolder(inflater.inflate(R.layout.item_user, parent, false))
                        }

                        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
                            holder.update(getItem(position), position)
                        }
                    }
                }
        recycler_view.adapter = mAdapter
        recycler_view.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        if (Preference.isTodayQueried()) {
            mPresenter.queryFromDatabase()
        } else {
            mPresenter.queryFromGithub()
        }
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun dismissLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun showUsers(users: List<User>) {
        mAdapter.submitList(users)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_revert) {
            recycler_view.scrollToPosition(if (mLayoutManager.reverseLayout) 0 else mAdapter.itemCount - 1)
            mLayoutManager.reverseLayout = !mLayoutManager.reverseLayout
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        mPresenter.unsubscribeAll()
        super.onDestroy()
    }
}
