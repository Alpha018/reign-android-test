package cl.alphacode.reigntest.di

import cl.alphacode.reigntest.repository.NewsRepository
import cl.alphacode.reigntest.repository.NewsRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun providerNewsRepository(repository: NewsRepositoryImp): NewsRepository
}