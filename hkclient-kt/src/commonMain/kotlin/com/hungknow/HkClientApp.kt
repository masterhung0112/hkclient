package com.hungknow

import com.hungknow.services.UserService
import com.hungknow.states.GlobalState
import io.ktor.client.engine.HttpClientEngine
import org.reduxkotlin.*

class HkClientApp {
    lateinit var globalStore: Store<GlobalState>
    lateinit var userService: UserService
    lateinit var hkClient: HkClient

    val reducer: Reducer<GlobalState> =  { state, action ->
        state
    }

    constructor(httpClientEngine: HttpClientEngine) {
        globalStore = createThreadSafeStore<GlobalState>(reducer, GlobalState(), applyMiddleware(createThunkMiddleware()))
        hkClient = HkClient(httpClientEngine)
        userService = UserService(this.globalStore, this.hkClient)
    }

    fun Users() = userService
}