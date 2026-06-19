package br.edu.ifsp.scl.sc3045153.postviewer.ui.screens.postdetail

data class PostDetailUiState(
    val comments: List<CommentUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)