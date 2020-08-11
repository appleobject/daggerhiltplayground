package com.appleobject.daggerhiltplayground.repository

import com.appleobject.daggerhiltplayground.model.Blog
import com.appleobject.daggerhiltplayground.retrofit.BlogRetrofit
import com.appleobject.daggerhiltplayground.retrofit.NetworkMapper
import com.appleobject.daggerhiltplayground.room.BlogDao
import com.appleobject.daggerhiltplayground.room.CacheMapper
import com.appleobject.daggerhiltplayground.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository
constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {
    suspend fun getBlog(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkBlogs = blogRetrofit.get()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for (blog in blogs){
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cachedBlogs = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        }catch (e: Exception){
            emit(DataState.Error(e))
        }
    }
}