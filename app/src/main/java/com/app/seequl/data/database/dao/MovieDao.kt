package com.app.seequl.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.app.seequl.constants.MovieSectionType
import com.app.seequl.data.model.MovieDTO

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: MovieDTO)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<MovieDTO>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(data: MovieDTO)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(data: List<MovieDTO>)

    @Query("SELECT * FROM movie_home WHERE section_type = :type")
    suspend fun trendingMovies(type: Int = MovieSectionType.TRENDING.value): List<MovieDTO>

    @Query("SELECT * FROM movie_home WHERE section_type = :type")
    suspend fun nowShowingMovies(type: Int = MovieSectionType.NOW_SHOWING.value): List<MovieDTO>

    @Query("SELECT * FROM movie_home WHERE section_type = :type")
    suspend fun topRatedMovies(type: Int = MovieSectionType.TOP_RATED.value): List<MovieDTO>

    @Query("SELECT * FROM movie_home WHERE id = :id")
    suspend fun movieById(id: Int): MovieDTO?

    @Query("SELECT * FROM movie_home WHERE bookmarked = 1")
    suspend fun bookmarkedMovies(): List<MovieDTO>

    @Query("UPDATE movie_home SET bookmarked = 1 WHERE id = :id")
    suspend fun bookmark(id: Int)

    @Query("UPDATE movie_home SET bookmarked = 0 WHERE id = :id")
    suspend fun unBookmark(id: Int)

    @Delete
    suspend fun delete(data: MovieDTO)

    @Delete
    suspend fun delete(data: List<MovieDTO>)

    @Query("DELETE FROM movie_home WHERE section_type = :type AND bookmarked = 0")
    suspend fun deleteAll(type: Int)

    @Query("DELETE FROM movie_home WHERE section_type = :type")
    suspend fun deleteForceAll(type: Int)

    @Query("DELETE FROM movie_home")
    suspend fun deleteAll()

}