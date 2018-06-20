package com.clink.jpinto.clink.room

class RoomGateContract {

    companion object {

        const val DATABASE_GATE = "gate.db"

        const val TABLE_GATES = "gates"

        private const val SELECT_COUNT = "SELECT COUNT(*) FROM "
        private const val SELECT_FROM = "SELECT * FROM "
        private const val DELETE_FROM = "DELETE FROM "

        const val SELECT_GATES_COUNT = SELECT_COUNT + TABLE_GATES
        const val SELECT_GATES = SELECT_FROM + TABLE_GATES
        const val DELETE_GATES = DELETE_FROM + TABLE_GATES

    }

}