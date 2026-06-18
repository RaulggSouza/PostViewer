package br.edu.ifsp.scl.sc3045153.postviewer.data.remote

import br.edu.ifsp.scl.sc3045153.postviewer.data.model.Comment
import br.edu.ifsp.scl.sc3045153.postviewer.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceholderApi {

    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts/{postId}/comments")
    suspend fun getCommentsByPostId(
        @Path("postId") postId: Int
    ): List<Comment>
}