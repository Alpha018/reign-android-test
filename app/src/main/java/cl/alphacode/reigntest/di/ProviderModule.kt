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
    private val baseUrl = "https://hn.algolia.com/api/v1/"

    @Provides
    @Named("BaseUrl")
    fun providerBaseUrl() = baseUrl.toHttpUrl()

    @Singleton
    @Provides
    fun providerRetrofit(@Named("BaseUrl") baseUrl: HttpUrl): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun providerNewsProvider(retrofit: Retrofit): NewsProvider =
        retrofit.create(NewsProvider::class.java)
}