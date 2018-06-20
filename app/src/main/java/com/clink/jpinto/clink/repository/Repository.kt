package com.clink.jpinto.clink.repository

import android.arch.lifecycle.LiveData
import com.clink.jpinto.clink.domain.Gate
import io.reactivex.Flowable

interface Repository {

    fun getGatesCount(): Flowable<Int>

    fun addGates(gateList:List<Gate>)

    fun getGatesList(): LiveData<List<Gate>>

    fun getGatesByCode(code :String):LiveData<Gate>

    fun deleteAll()

}