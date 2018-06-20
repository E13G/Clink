package com.clink.jpinto.clink.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [RoomGateEntity::class], version = 1, exportSchema = false)
abstract class RoomGateDatabase : RoomDatabase() {

    abstract fun gateDao(): RoomGateDao

    companion object {
        fun buildPersistentGate(context: Context): RoomGateDatabase = Room.databaseBuilder(
                context.applicationContext,
                RoomGateDatabase::class.java,
                RoomGateContract.DATABASE_GATE
        ).build()
    }
}