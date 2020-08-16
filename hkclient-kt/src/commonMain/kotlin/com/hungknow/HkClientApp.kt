package com.hungknow

import com.hungknow.services.UserService
import com.hungknow.states.GlobalState
import org.reduxkotlin.*

class HkClientApp {
    lateinit var globalStore: Store<GlobalState>
    lateinit var userService: UserService

    val reducer: Reducer<GlobalState> =  { state, action ->
        state
    }

    init {
        globalStore = createThreadSafeStore<GlobalState>(reducer, GlobalState(), applyMiddleware(createThunkMiddleware()))
        userService = UserService(this.globalStore)
    }

    fun Users() = userService
}