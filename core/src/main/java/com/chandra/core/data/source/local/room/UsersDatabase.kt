package com.chandra.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chandra.core.data.source.local.entity.UsersEntity

@Database(entities = [UsersEntity::class], version = 1, exportSchema = false)
abstract class UsersDatabase: RoomDatabase() {
    abstract fun userDao(): UsersDao
}