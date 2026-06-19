package br.edu.ifsp.scl.sc3045153.postviewer.data.repository

import br.edu.ifsp.scl.sc3045153.postviewer.data.local.LocalCommentDao
import br.edu.ifsp.scl.sc3045153.postviewer.data.local.LocalCommentEntity
import br.edu.ifsp.scl.sc3045153.postviewer.data.model.Comment
import br.edu.ifsp.scl.sc3045153.postviewer.data.model.Post
import br.edu.ifsp.scl.sc3045153.postviewer.data.model.PostModel
import br.edu.ifsp.scl.sc3045153.postviewer.data.remote.ApiClient
import br.edu.ifsp.scl.sc3045153.postviewer.data.remote.JsonPlaceholderApi
import kotlinx.coroutines.flow.Flow

class PostRepository(
    private val api: JsonPlaceholderApi = ApiClient.api,
    private val localCommentDao: LocalCommentDao? = null
) {
    suspend fun getPosts(): List<PostModel> {
        return api.getPosts().map { post ->
            PostModel(
                post.userId,
                post.id,
                post.title,
                post.body,
                getCommentsByPostId(post.id).size
            )
        }
    }

    suspend fun getCommentsByPostId(postId: Int): List<Comment> {
        return api.getCommentsByPostId(postId)
    }

    fun getLocalCommentsByPostId(postId: Int): Flow<List<LocalCommentEntity>> {
        return requireNotNull(localCommentDao) {
            "LocalCommentDao is required to access local comments."
        }.getCommentsByPostId(postId)
    }

    suspend fun addLocalComment(comment: LocalCommentEntity) {
        requireNotNull(localCommentDao) {
            "LocalCommentDao is required to add local comments."
        }.insertComment(comment)
    }
}