package br.edu.ifsp.scl.sc3045153.postviewer.ui.screens.postdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.ifsp.scl.sc3045153.postviewer.data.model.Comment
import br.edu.ifsp.scl.sc3045153.postviewer.ui.screens.viewmodel.PostDetailsViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

@Composable
fun PostDetailRoute(
    postId: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PostDetailsViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(postId) {
        viewModel.loadComments(postId)
    }

    PostDetailScreen(
        postId = postId,
        uiState = uiState,
        onBackClick = onBackClick,
        onRetryClick = {
            viewModel.loadComments(postId)
        },
        modifier = modifier
    )
}

@Composable
fun PostDetailScreen(
    postId: Int,
    uiState: PostDetailUiState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = "Comentários do Post $postId",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Button(onClick = onBackClick, modifier = Modifier.padding(top = 12.dp)) {
            Text(text = "Voltar")
        }

        when {
            uiState.isLoading -> {
                LoadingComments()
            }

            uiState.errorMessage != null -> {
                ErrorComments(
                    message = uiState.errorMessage,
                    onRetryClick = onRetryClick
                )
            }

            else -> {
                CommentList(
                    comments = uiState.comments
                )
            }
        }
    }
}

@Composable
private fun LoadingComments() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorComments(message: String, onRetryClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )

        Button(onClick = onRetryClick, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Tentar novamente")
        }
    }
}

@Composable
private fun CommentList(comments: List<Comment>) {
    LazyColumn(
        contentPadding = PaddingValues(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(comments) { comment ->
            CommentItem(comment = comment)
        }
    }
}

@Composable
private fun CommentItem(comment: Comment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = comment.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = comment.email,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = comment.body,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

        }
    }
}

@Preview
@Composable
private fun PostDetailScreenPrev() {
    val viewModel: PostDetailsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    PostDetailScreen(1, uiState, {}, {})
}