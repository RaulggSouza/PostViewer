package br.edu.ifsp.scl.sc3045153.postviewer.ui.navigation

object AppRoutes {
    const val POST_LIST = "post_list"
    const val POST_DETAIL = "post_detail"
    const val POST_ID_ARGUMENT = "postId"

    const val POST_DETAIL_WITH_ARGUMENT = "$POST_DETAIL/{$POST_ID_ARGUMENT}"

    fun postDetailRoute(postId: Int): String {
        return "$POST_DETAIL/$postId"
    }
}