package cl.alphacode.reigntest.di

import cl.alphacode.reigntest.provider.NewsProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class ProviderModule {

    private val baseUrl get() = BASE_URL.toHttpUrl()

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

    companion object {
        private const val BASE_URL = "https://hn.algolia.com/api/v1/"
    }
}