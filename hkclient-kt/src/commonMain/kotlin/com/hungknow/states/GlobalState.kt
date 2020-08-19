package com.hungknow.states

import kotlinx.serialization.Serializable
import org.reduxkotlin.Reducer

@Serializable
data class GlobalState(
//        val entities: EntityState = EntityState(),
val users: UsersState = UsersState()
)

data class GlobalReducers(
        val reducers: Reducer<UsersState>
)