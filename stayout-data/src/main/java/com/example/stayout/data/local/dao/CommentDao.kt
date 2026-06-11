package com.example.stayout.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stayout.data.local.entity.CommentEntity

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments WHERE postId = :postId")
    suspend fun getByPostId(postId: Int): List<CommentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(comments: List<CommentEntity>)

    @Query("DELETE FROM comments WHERE postId = :postId")
    suspend fun deleteByPostId(postId: Int)
}
