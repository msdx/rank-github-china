package com.githang.rankgithubchina.api

import com.google.gson.annotations.SerializedName
import com.litesuits.orm.db.annotation.Column
import com.litesuits.orm.db.annotation.Conflict
import com.litesuits.orm.db.annotation.Table
import com.litesuits.orm.db.annotation.Unique
import com.litesuits.orm.db.enums.Strategy

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2018-05-17
 */
@Table("t_user")
data class User(
        @Conflict(Strategy.REPLACE) @Unique @Column("id") @SerializedName("id") val id: String,
        @Column("username") @SerializedName("username") val username: String,
        @Column("fullname") @SerializedName("fullname") val fullName: String,
        @Column("location") @SerializedName("location") val location: String,
        @Column("language") @SerializedName("language") val language: String,
        @Column("repos") @SerializedName("repos") val repos: Int,
        @Column(COL_FOLLOWERS) @SerializedName("followers") val followers: Int,
        @Column("created") @SerializedName("created") val createdTime: String
) {
    companion object {
        const val COL_FOLLOWERS = "followers"
    }
}