package com.githang.rankgithubchina.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

/**
 * @author 黄浩杭 (huanghaohang@parkingwang.com)
 * @since 2018-05-17
 */
interface UserService {
    @GET("legacy/user/search/location:{location}")
    fun users(@Path("location") location: String,
              @Query("start_page") startPage: Int,
              @Query("sort") sort: String = "followers",
              @Query("order") order: String = "desc"): Observable<UserResponse>

}