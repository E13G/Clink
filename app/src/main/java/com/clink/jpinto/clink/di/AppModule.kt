package com.clink.jpinto.clink.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val gateApplication: GateApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = gateApplication

}