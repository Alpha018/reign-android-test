package cl.alphacode.reigntest.dao

import androidx.room.*
import cl.alphacode.reigntest.entity.News
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM News WHERE deleted_at IS NULL")
    fun getAllNews(): Flow<List<News>>

    @Insert
    fun insertNews(news: News)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNewsOnConflict(news: News)

    @Update
    fun updateNews(news: News)
}