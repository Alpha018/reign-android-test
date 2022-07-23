package cl.alphacode.reigntest.di

import android.content.Context
import androidx.room.Room
import cl.alphacode.reigntest.dao.NewsDao
import cl.alphacode.reigntest.database.AppDatabase
import cl.alphacode.reigntest.entity.Converters
import cl.alphacode.reigntest.provider.NewsProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProviderModule {

    private val baseUrl = BASE_URL.toHttpUrl()

    @Singleton
    @Provides
    fun providerRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun providerNewsProvider(retrofit: Retrofit): NewsProvider =
        retrofit.create(NewsProvider::class.java)

    @Provides
    fun provideNewsDao(appDatabase: AppDatabase): NewsDao {
        return appDatabase.newsDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "NewsReader"
        )
            .build()
    }

    @Provides
    @Named("ListScreenDispatcher")
    fun provideNewsDispatcher(): CoroutineDispatcher = Dispatchers.IO

    companion object {
        private const val BASE_URL = "https://hn.algolia.com/api/v1/"
    }
}