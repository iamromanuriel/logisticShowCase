package com.example.logisticshowcase.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.logisticshowcase.data.db.BaseDao
import com.example.logisticshowcase.data.db.entity.ClientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {
    @Query("SELECT * FROM Client WHERE id = :id")
    fun getClientById(id: Int): Flow<ClientEntity>
}