package com.appleobject.daggerhiltplayground.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BlogCacheEntity::class], version = 1)
abstract class BlogDatabase: RoomDatabase() {

    abstract fun BlogDao(): BlogDao

    companion object{
        val DATABASE_NAME = "Blog_db"
    }
}