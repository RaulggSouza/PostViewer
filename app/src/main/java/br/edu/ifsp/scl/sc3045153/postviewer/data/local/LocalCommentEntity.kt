package br.edu.ifsp.scl.sc3045153.postviewer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_comments")
data class LocalCommentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)