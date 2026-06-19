package br.edu.ifsp.scl.sc3045153.postviewer.ui.screens.postlist

import br.edu.ifsp.scl.sc3045153.postviewer.data.model.PostModel

data class PostListUiState(
    val posts: List<PostModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)