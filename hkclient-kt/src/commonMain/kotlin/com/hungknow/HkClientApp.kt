package com.hungknow

import com.hungknow.services.UserService
import com.hungknow.states.GlobalState
import io.ktor.client.engine.HttpClientEngine
import org.reduxkotlin.*

class ReducersMapObject<S, A> {
//    [K in keyof S]: Reducer<S[K], A>
}

//inline fun <reified State> combineReducers(reducers: Map<String, Reducer<State>>): Reducer<State> {
//    val reducerKeys = reducers.keys
//    return {
//            state: Any,
//            action: Any
//     ->
//        for (prop in State::class.members) {
//            prop.name
//        }
//        var hasChanged = false
//        reducers.forEach { entry ->
//            val key = entry.key
//            val reducer = entry.value
//            var previousStateForKey = state.key
//            val nextStateForKey = reducer(previousStateForKey, action)
//            nextState[key] = nextStateForKey
//            hasChanged = hasChanged || nextStateForKey !== previousStateForKey
//        }
//    }
//}
//        { state, action ->
//            reducers.fold(state, { s, reducer -> reducer(s, action) })
//        }

class HkClientApp {
    lateinit var globalStore: Store<GlobalState>
    lateinit var userService: UserService
    lateinit var hkClient: HkClient

    val reducer: Reducer<GlobalState> =  { state, action ->
        state
    }

    val dummyReducer: (state: GlobalState, action: Any) -> GlobalState = { s, a -> s }

    constructor(httpClientEngine: HttpClientEngine?) {
        hkClient = HkClient(httpClientEngine)
        userService = UserService(this.hkClient)
        globalStore = createStore<GlobalState>(::globalReducer, GlobalState(), applyMiddleware(createThunkMiddleware()))
        this.userService.setGlobalStore(this.globalStore)
//        this.globalStore.replaceReducer(::globalReducer)
    }

    class ExtractStateInfo<S, T> (
            val extractState: (state: S) -> T,
            val reducer: (state: T, action: Any) -> T
    )

    fun  extractState(globalState: GlobalState) {
//        val userHandler = ExtractStateInfo(
//                { s: GlobalState -> s.users })
    }

    fun globalReducer(state: GlobalState, action: Any): GlobalState {
        var hasChange = false
        val usersState = this.userService.mainReducer(state.entities.users, action)
        hasChange = hasChange || usersState != state.entities.users

        if (hasChange) {
            return state.copy(
                    entities = state.entities.copy(
                        users = usersState
                    )
            )
        }
        return state
    }

    fun Users() = userService
}