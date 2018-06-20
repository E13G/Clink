package com.clink.jpinto.clink.di

import android.content.Context
import com.clink.jpinto.clink.room.RoomGateDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomGateDatabase(context: Context) =
            RoomGateDatabase.buildPersistentGate(context)
}