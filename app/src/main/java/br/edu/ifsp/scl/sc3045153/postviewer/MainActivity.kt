package br.edu.ifsp.scl.sc3045153.postviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.edu.ifsp.scl.sc3045153.postviewer.data.model.Post
import br.edu.ifsp.scl.sc3045153.postviewer.ui.screens.postlist.PostListScreen
import br.edu.ifsp.scl.sc3045153.postviewer.ui.theme.PostViewerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PostViewerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PostViewerApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PostViewerApp(modifier: Modifier = Modifier) {
    val fakePosts = listOf(
        Post(
            userId = 1,
            id = 1,
            title = "Primeiro post",
            body = "Este é um exemplo de post sendo exibido na tela."
        ),
        Post(
            userId = 1,
            id = 2,
            title = "Segundo post",
            body = "Depois vamos trocar esses dados falsos pelos dados da API."
        ),
        Post(
            userId = 2,
            id = 3,
            title = "Terceiro post",
            body = "Cada item aparece dentro de um Card clicável."
        )
    )

    PostListScreen(
        posts = fakePosts,
        onPostClick = { post ->
            println("Post clicado: ${post.id}")
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PostViewerAppPrev() {
    PostViewerTheme {
        PostViewerApp()
    }
}