package com.msnegi.hiltmvvm.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.msnegi.hiltmvvm.models.Product

@Database(entities = [Product::class], version = 1)
abstract class FakerDB : RoomDatabase() {

    abstract fun getFakerDAO() : FakerDAO

}