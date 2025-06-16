package com.app.seequl.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.seequl.data.model.BookmarkEntity

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: BookmarkEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun bookmark(movie: BookmarkEntity)

    @Query("SELECT * FROM bookmarked_movies")
    fun getAllBookmarks(): LiveData<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmarked_movies")
    suspend fun getAllBookmarksOnce(): List<BookmarkEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarked_movies WHERE id = :movieId)")
    fun isBookmarked(movieId: Int): LiveData<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarked_movies WHERE id = :movieId)")
    fun isBookmarkedOnce(movieId: Int): Boolean

    @Query("SELECT * FROM bookmarked_movies WHERE id = :movieId LIMIT 1")
    suspend fun getMovieById(movieId: Int): BookmarkEntity?

    @Query("DELETE FROM bookmarked_movies WHERE id = :movieId")
    fun unBookmark(movieId: Int)

    @Query("DELETE FROM bookmarked_movies WHERE id = :movieId")
    fun delete(movieId: Int)

    @Delete
    suspend fun delete(movie: BookmarkEntity)

    @Query("DELETE FROM bookmarked_movies")
    suspend fun deleteAll()
}