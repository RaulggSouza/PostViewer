package br.edu.ifsp.scl.sc3045153.postviewer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalCommentDao {

    @Insert
    suspend fun insertComment(comment: LocalCommentEntity)

    @Query("SELECT * FROM local_comments WHERE postId = :postId ORDER BY id DESC")
    fun getCommentsByPostId(postId: Int): Flow<List<LocalCommentEntity>>
}