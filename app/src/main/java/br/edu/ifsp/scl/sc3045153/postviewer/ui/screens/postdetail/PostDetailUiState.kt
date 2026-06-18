package br.edu.ifsp.scl.sc3045153.postviewer.ui.screens.postdetail

import br.edu.ifsp.scl.sc3045153.postviewer.data.model.Comment

data class PostDetailUiState(
    val comments: List<Comment> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)