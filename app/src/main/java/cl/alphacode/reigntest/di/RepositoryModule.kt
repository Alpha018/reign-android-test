package cl.alphacode.reigntest.di

import cl.alphacode.reigntest.provider.NewsProvider
import cl.alphacode.reigntest.repository.NewsRepository
import cl.alphacode.reigntest.repository.NewsRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun providerNewsRepository(provider: NewsProvider): NewsRepository = NewsRepositoryImp(provider)
}