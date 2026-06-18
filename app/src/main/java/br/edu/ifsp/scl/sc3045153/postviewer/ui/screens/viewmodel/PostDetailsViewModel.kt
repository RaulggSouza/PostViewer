package br.edu.ifsp.scl.sc3045153.postviewer.ui.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.sc3045153.postviewer.data.repository.PostRepository
import br.edu.ifsp.scl.sc3045153.postviewer.ui.screens.postdetail.PostDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostDetailsViewModel(private val repository: PostRepository = PostRepository()): ViewModel() {

    private val _uiState = MutableStateFlow(PostDetailUiState())
    val uiState: StateFlow<PostDetailUiState> = _uiState.asStateFlow()

    fun loadComments(postId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val comments = repository.getCommentsByPostId(postId)

                _uiState.value = _uiState.value.copy(
                    comments = comments,
                    isLoading = false
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Não foi possível carregar os comentários."
                )
            }
        }
    }
}
