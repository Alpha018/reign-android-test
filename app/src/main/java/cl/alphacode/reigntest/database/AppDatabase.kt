package cl.alphacode.reigntest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cl.alphacode.reigntest.dao.NewsDao
import cl.alphacode.reigntest.entity.Converters
import cl.alphacode.reigntest.entity.News

@Database(entities = [News::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}