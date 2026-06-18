package br.edu.ifsp.scl.sc3045153.postviewer.data.repository

import br.edu.ifsp.scl.sc3045153.postviewer.data.model.Comment
import br.edu.ifsp.scl.sc3045153.postviewer.data.model.Post
import br.edu.ifsp.scl.sc3045153.postviewer.data.remote.ApiClient
import br.edu.ifsp.scl.sc3045153.postviewer.data.remote.JsonPlaceholderApi

class PostRepository(
    private val api: JsonPlaceholderApi = ApiClient.api
) {
    suspend fun getPosts(): List<Post> {
        return api.getPosts()
    }

    suspend fun getCommentsByPostId(postId: Int): List<Comment> {
        return api.getCommentsByPostId(postId)
    }
}