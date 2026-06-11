package com.example.stayout.data.remote.api

import com.example.stayout.data.remote.model.CommentDataModel
import com.example.stayout.data.remote.model.UserDataModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentApi {
    @GET("posts/{postId}/comments")
    fun getComments(
        @Path("postId") postId: Int,
    ): Single<List<CommentDataModel>>

    @GET("users")
    fun getUsers(): Single<List<UserDataModel>>
}
