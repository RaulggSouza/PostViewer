package br.edu.ifsp.scl.sc3045153.postviewer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalCommentEntity::class],
    version = 1
)
abstract class PostViewerDatabase : RoomDatabase() {

    abstract fun localCommentDao(): LocalCommentDao
}