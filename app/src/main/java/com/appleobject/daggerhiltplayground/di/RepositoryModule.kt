package com.appleobject.daggerhiltplayground.di

import com.appleobject.daggerhiltplayground.repository.MainRepository
import com.appleobject.daggerhiltplayground.retrofit.BlogRetrofit
import com.appleobject.daggerhiltplayground.retrofit.NetworkMapper
import com.appleobject.daggerhiltplayground.room.BlogDao
import com.appleobject.daggerhiltplayground.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        blogRetrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository
    {
        return MainRepository(blogDao, blogRetrofit, cacheMapper, networkMapper)
    }
}