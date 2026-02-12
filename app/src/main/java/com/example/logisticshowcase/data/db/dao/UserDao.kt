package com.example.logisticshowcase.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.logisticshowcase.data.db.BaseDao
import com.example.logisticshowcase.data.db.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao: BaseDao<UserEntity> {

    @Query("SELECT * FROM User WHERE id = :id")
    fun getUser(id: Int): Flow<UserEntity>
}