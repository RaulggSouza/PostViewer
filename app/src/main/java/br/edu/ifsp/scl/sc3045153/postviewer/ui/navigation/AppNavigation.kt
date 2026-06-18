package br.edu.ifsp.scl.sc3045153.postviewer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.edu.ifsp.scl.sc3045153.postviewer.ui.screens.postdetail.PostDetailRoute
import br.edu.ifsp.scl.sc3045153.postviewer.ui.screens.postlist.PostListRoute

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.POST_LIST,
        modifier = modifier
    ) {
        composable(route = AppRoutes.POST_LIST) {
            PostListRoute(
                onPostClick = { post ->
                    navController.navigate(
                        AppRoutes.postDetailRoute(post.id)
                    )
                },
                modifier = modifier
            )
        }

        composable(
            route = AppRoutes.POST_DETAIL_WITH_ARGUMENT,
            arguments = listOf(
            navArgument(AppRoutes.POST_ID_ARGUMENT) {
                type = NavType.IntType
            }
            )
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getInt(
                AppRoutes.POST_ID_ARGUMENT
            ) ?: 0

            PostDetailRoute(
                postId = postId,
                onBackClick = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
    }
}