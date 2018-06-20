package com.clink.jpinto.clink.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull


@Entity(tableName = RoomGateContract.TABLE_GATES)
data class RoomGateEntity(@PrimaryKey
                          (autoGenerate = true)
                          val id: Long,

                          @NonNull
                          var code: String,

                          @NonNull
                          var name: String,

                          @NonNull
                          var category: String,

                          @NonNull
                          var description: String,

                          @NonNull
                          var latitude: Double,

                          @NonNull
                          var longitude: Double)

