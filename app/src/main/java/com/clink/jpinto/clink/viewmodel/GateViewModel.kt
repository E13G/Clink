package com.clink.jpinto.clink.viewmodel

import android.arch.lifecycle.*
import com.clink.jpinto.clink.di.GateApplication
import com.clink.jpinto.clink.domain.Gate
import com.clink.jpinto.clink.repository.GateRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class GateViewModel: ViewModel(), LifecycleObserver {

    @Inject
    lateinit var gateRepository: GateRepository

    private val compositeDisposable = CompositeDisposable()
    lateinit var liveGateData: LiveData<List<Gate>>

    init {
        initializeDagger()
    }

    fun loadGateList(): LiveData<List<Gate>>? {
        if (!::liveGateData.isInitialized) {
            liveGateData = MutableLiveData<List<Gate>>()
            liveGateData = gateRepository.getGatesList()
        }
        return liveGateData
    }

    fun loadGateByCode(code:String):LiveData<Gate> = gateRepository.getGatesByCode(code)

    fun addGate(gate:Gate){

        var gateList = listOf(gate)
        gateRepository.addGates(gateList)
    }

    fun deleteAll(){

        gateRepository.deleteAll()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unSubscribeViewModel() {
        for (disposable in gateRepository.allCompositeDisposable) {
            compositeDisposable.addAll(disposable)
        }
        compositeDisposable.clear()
    }

    private fun isRoomEmpty(gatesTotal: Int) = gatesTotal == 0


    override fun onCleared() {
        unSubscribeViewModel()
        super.onCleared()
    }


    private fun initializeDagger() = GateApplication.appComponent.inject(this)

}