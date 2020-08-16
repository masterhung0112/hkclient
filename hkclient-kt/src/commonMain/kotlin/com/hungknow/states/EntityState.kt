package com.hungknow.states

import kotlinx.serialization.Serializable

@Serializable
data class EntityState(
        val users: UsersState = UsersState()
)