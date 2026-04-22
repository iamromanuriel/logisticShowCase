package com.example.logisticshowcase.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.logisticshowcase.data.db.dao.ClientDao
import com.example.logisticshowcase.data.db.dao.MessageDao
import com.example.logisticshowcase.data.db.dao.OrderDao
import com.example.logisticshowcase.data.db.dao.OrderItemDao
import com.example.logisticshowcase.data.db.dao.UserDao
import com.example.logisticshowcase.data.db.dao.VehicleDao
import com.example.logisticshowcase.data.db.entity.ClientEntity
import com.example.logisticshowcase.data.db.entity.MessageEntity
import com.example.logisticshowcase.data.db.entity.OrderEntity
import com.example.logisticshowcase.data.db.entity.OrderItemEntity
import com.example.logisticshowcase.data.db.entity.UserEntity
import com.example.logisticshowcase.data.db.entity.Vehicle

@Database(
    entities = [
        ClientEntity::class,
        MessageEntity::class,
        OrderEntity::class,
        OrderItemEntity::class,
        UserEntity::class,
        Vehicle::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun  clientDao(): ClientDao
    abstract fun messageDao(): MessageDao
    abstract fun orderDao(): OrderDao
    abstract fun orderItemDao(): OrderItemDao
    abstract fun userDao(): UserDao
    abstract fun vehicleDao(): VehicleDao
}