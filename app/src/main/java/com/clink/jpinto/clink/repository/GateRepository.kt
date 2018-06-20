package com.clink.jpinto.clink.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import android.util.Log
import com.clink.jpinto.clink.domain.Gate
import com.clink.jpinto.clink.room.RoomGateDao
import com.clink.jpinto.clink.room.RoomGateDatabase
import com.clink.jpinto.clink.room.RoomGateEntity
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GateRepository @Inject constructor(
        private val roomGateDatabase: RoomGateDatabase

) : Repository {

    val tag : String = "Gate Repository"
    val allCompositeDisposable: MutableList<Disposable> = arrayListOf()

    override fun getGatesByCode(code: String): LiveData<Gate> {


        val roomGateDao = roomGateDatabase.gateDao()
        val mutableLiveData = MutableLiveData<Gate>()
        val disposable = roomGateDao.getGatesByCode(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ gateList ->
                    mutableLiveData.value = createGate(gateList)
                }, { t: Throwable? -> t!!.printStackTrace() })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    override fun getGatesCount(): Flowable<Int> = roomGateDatabase.gateDao().getGatesCount()


    override fun addGates(gateList: List<Gate>) {

        InsertAsyncTask(roomGateDatabase.gateDao()).execute(transformGateIntoEntity(gateList))
    }

    override fun getGatesList(): LiveData<List<Gate>> {

        val roomGateDao = roomGateDatabase.gateDao()
        val mutableLiveData = MutableLiveData<List<Gate>>()
        val disposable = roomGateDao.getAllGates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ gateList ->
                    mutableLiveData.value = transformEntityIntoGate(gateList)
                }, { t: Throwable? -> t!!.printStackTrace() })
        allCompositeDisposable.add(disposable)
        return mutableLiveData
    }

    override fun deleteAll() {

        val gateList = roomGateDatabase.gateDao().getAllGates().blockingFirst()
        Log.e(tag,gateList.toString())
        DeleteAsyncTask(roomGateDatabase.gateDao()).execute(gateList)

    }



    private fun transformEntityIntoGate(gates: List<RoomGateEntity>): List<Gate> {
        Log.e(tag,"transformEntityIntoGate")
        val gateList = ArrayList<Gate>()
        gates.forEach {
            gateList.add(Gate(it.code, it.name,it.category,
                    it.description,it.latitude,it.longitude))
        }
        return gateList
    }

    private fun transformGateIntoEntity(gates: List<Gate>): List<RoomGateEntity> {
        Log.e(tag,"transformGateIntoEntity")
        val gateList = ArrayList<RoomGateEntity>()
        gates.forEach {
            gateList.add(createGateEntity(it))
        }
        return gateList
    }

    private fun createGate(gate: RoomGateEntity) =
            Gate(gate.code,gate.name, gate.category,
                    gate.description, gate.latitude, gate.longitude)


    private fun createGateEntity(gate: Gate) =
            RoomGateEntity(0, gate.code,
                    gate.name, gate.category, gate.description,
                    gate.latitude, gate.longitude)

    private class DeleteAsyncTask internal constructor(val gateDao: RoomGateDao) : AsyncTask<List<RoomGateEntity>, Void, Void>() {

        override fun doInBackground(vararg params: List<RoomGateEntity>): Void? {
            gateDao.deleteAll(params[0])
            return null
        }
    }

    private class InsertAsyncTask internal constructor(val gateDao: RoomGateDao) : AsyncTask<List<RoomGateEntity>, Void, Void>() {

        override fun doInBackground(vararg params: List<RoomGateEntity>): Void? {
            gateDao.insertAll(params[0])
            return null
        }
    }

}