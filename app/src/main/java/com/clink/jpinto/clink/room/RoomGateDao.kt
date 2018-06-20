package com.clink.jpinto.clink.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable



@Dao
interface RoomGateDao {

    @Query(RoomGateContract.SELECT_GATES + " where code = :code LIMIT 1")
    fun getGatesByCode(code :String): Flowable<RoomGateEntity>

    @Query(RoomGateContract.SELECT_GATES)
    fun getAllGates(): Flowable<List<RoomGateEntity>>

    @Query(RoomGateContract.SELECT_GATES_COUNT)
    fun getGatesCount(): Flowable<Int>

    @Insert()
    fun insertAll(gates: List<RoomGateEntity>)

    @Delete
    fun delete(gate: RoomGateEntity)

    @Delete
    fun deleteAll(gates: List<RoomGateEntity>)
}