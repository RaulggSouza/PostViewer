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

    fun onNewCommentBodyChange(body: String) {
        _uiState.value = _uiState.value.copy(
            newCommentBody = body
        )
    }

    fun addLocalComment(postId: Int) {
        val body = _uiState.value.newCommentBody.trim()

        if (body.isBlank()) {
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isAddingComment = true,
                errorMessage = null
            )

            try {
                val localComment = LocalCommentEntity(
                    postId = postId,
                    name = "Você",
                    email = "comentario-local@postviewer.app",
                    body = body
                )

                repository.addLocalComment(localComment)

                _uiState.value = _uiState.value.copy(
                    newCommentBody = "",
                    isAddingComment = false
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isAddingComment = false,
                    errorMessage = "Não foi possível adicionar o comentário."
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