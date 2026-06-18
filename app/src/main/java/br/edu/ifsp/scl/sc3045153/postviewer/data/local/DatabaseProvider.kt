package br.edu.ifsp.scl.sc3045153.postviewer.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var database: PostViewerDatabase? = null

    fun getDatabase(context: Context): PostViewerDatabase {
        return database ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                PostViewerDatabase::class.java,
                "post_viewer_database"
            ).build()

            database = instance
            instance
        }
    }
}