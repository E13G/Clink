package com.clink.jpinto.clink.di

import com.clink.jpinto.clink.viewmodel.GateViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class, RoomModule::class))
@Singleton
interface AppComponent {

    fun inject(currencyViewModel: GateViewModel)

}