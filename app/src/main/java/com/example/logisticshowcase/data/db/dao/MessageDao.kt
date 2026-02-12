package com.example.logisticshowcase.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.logisticshowcase.data.db.BaseDao
import com.example.logisticshowcase.data.db.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM Message WHERE id = :id")
    fun getMessageById(id: Int): Flow<MessageEntity>

}