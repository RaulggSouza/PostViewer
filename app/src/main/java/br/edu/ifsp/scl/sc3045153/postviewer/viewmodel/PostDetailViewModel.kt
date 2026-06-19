package br.edu.ifsp.scl.sc3045153.postviewer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifsp.scl.sc3045153.postviewer.data.local.LocalCommentEntity
import br.edu.ifsp.scl.sc3045153.postviewer.data.model.Comment
import br.edu.ifsp.scl.sc3045153.postviewer.data.repository.PostRepository
import br.edu.ifsp.scl.sc3045153.postviewer.ui.screens.postdetail.CommentUiModel
import br.edu.ifsp.scl.sc3045153.postviewer.ui.screens.postdetail.PostDetailUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostDetailViewModel(private val repository: PostRepository = PostRepository()): ViewModel() {

    private val _uiState = MutableStateFlow(PostDetailUiState())
    val uiState: StateFlow<PostDetailUiState> = _uiState.asStateFlow()

    private var commentsJob: Job? = null

    fun loadComments(postId: Int) {
        commentsJob?.cancel()

        commentsJob = viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {
                val apiComments = repository
                    .getCommentsByPostId(postId)
                    .map { comment -> comment.toUiModel() }

                repository
                    .getLocalCommentsByPostId(postId)
                    .collect { localComments ->
                        val localCommentUiModels = localComments.map { localComments ->
                            localComments.toUiModel()
                        }
                        _uiState.value = _uiState.value.copy(
                            comments = apiComments + localCommentUiModels,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Não foi possível carregar os comentários."
                )
            }
        }
    }
}

private fun Comment.toUiModel(): CommentUiModel {
    return CommentUiModel(
        id = id,
        name = name,
        email = email,
        body = body,
        isLocal = false
    )
}

private fun LocalCommentEntity.toUiModel(): CommentUiModel {
    return CommentUiModel(
        id = id,
        name = name,
        email = email,
        body = body,
        isLocal = true
    )
}