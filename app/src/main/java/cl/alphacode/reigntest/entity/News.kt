package cl.alphacode.reigntest.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices = [Index(value = ["created_at_i"], unique = true)])
data class News (
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "story_url") val storyUrl: String,
    @ColumnInfo(name = "created_at") val createdAt: Date,
    @ColumnInfo(name = "created_at_i") val createdAtI: Int,
    @ColumnInfo(name = "deleted_at", defaultValue = "NULL") val deletedAt: Date? = null,
)