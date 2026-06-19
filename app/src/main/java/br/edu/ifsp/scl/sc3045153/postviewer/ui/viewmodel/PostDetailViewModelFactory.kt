package br.edu.ifsp.scl.sc3045153.postviewer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.scl.sc3045153.postviewer.data.repository.PostRepository

class PostDetailViewModelFactory(
    private val repository: PostRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostDetailViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}