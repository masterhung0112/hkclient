package com.hungknow.states

import kotlinx.serialization.Serializable

@Serializable
data class GlobalState(
        val entities: EntityState = EntityState()
)