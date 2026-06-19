package br.edu.ifsp.scl.sc3045153.postviewer.ui.screens.postdetail

data class CommentUiModel(
    val id: Int,
    val name: String,
    val email: String,
    val body: String,
    val isLocal: Boolean
)