package com.githang.rankgithubchina.main

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.githang.rankgithubchina.R
import com.githang.rankgithubchina.api.Constant
import com.githang.rankgithubchina.api.User
import java.text.SimpleDateFormat
import java.util.TimeZone

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2018-05-17
 */
class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mAvatar: ImageView = itemView.findViewById(R.id.avatar)
    private val mFullName: TextView = itemView.findViewById(R.id.full_name)
    private val mUsername: TextView = itemView.findViewById(R.id.username)
    private val mRepos: TextView = itemView.findViewById(R.id.repos)
    private val mLocation: TextView = itemView.findViewById(R.id.location)
    private val mFollowers: TextView = itemView.findViewById(R.id.followers)
    private val mRank: TextView = itemView.findViewById(R.id.rank)
    private val mLanguage: TextView = itemView.findViewById(R.id.language)
    private val mContext = itemView.context
    private val mAvatarSize = lazy { mAvatar.layoutParams.height }

    fun update(user: User, position: Int) {
        Glide.with(mContext)
                .load(Constant.getAvatarUrl(user.id, mAvatarSize.value))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.avatar)
                .into(mAvatar)
        mFullName.text = user.fullName
        mUsername.text = "(${user.username})"
        mRepos.text = user.repos.toString()
        mLocation.text = user.location
        mFollowers.text = user.followers.toString()
        mRank.text = position.plus(1).toString()
        mLanguage.text = user.language
    }
}