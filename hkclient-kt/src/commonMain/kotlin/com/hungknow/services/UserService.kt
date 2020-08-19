package com.hungknow.services

import com.hungknow.HkClient
import com.hungknow.models.UserProfile
import com.hungknow.states.GlobalState
import com.hungknow.states.UsersState
import kotlinx.coroutines.*
import org.reduxkotlin.*
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KFunction

data class ActionHelp(val action: Any, val payload: Any)

class UserService : CoroutineScope {

    lateinit var userReducer: Reducer<UsersState>
    lateinit var actionMap: Map<String, KFunction<Any>>
    val receivedProfile: (userProfile: UserProfile) -> Unit = {
        this.store.dispatch(ActionHelp(this, it))
    }
    lateinit var store: Store<GlobalState>
    lateinit var hkClient: HkClient

    constructor(store: Store<GlobalState>, hkClient: HkClient) {
        // Register the reducers
        userReducer = combineReducers(::exampleReducer, ::exampleReducer1)
        actionMap = mapOf(
                "hello" to ::exampleReducer
        )
        var f = actionMap.get("hello")
//        f!!.call("hello")
        this.store = store
        this.hkClient = hkClient
    }

    fun exampleReducer(previousState: UsersState, action: Any): UsersState {
        val helperAction = action as ActionHelp
        when (helperAction.action) {
            receivedProfile -> {
                getReceivedProfile(previousState.profiles, helperAction.payload as UserProfile)
            }
        }
        return previousState
    }

    fun exampleReducer1(previousState: UsersState, action: Any): UsersState {
        return previousState
    }

    fun createReducersCallable() {

    }

    //
    fun getReceivedProfile(previousState: Map<String, UserProfile>, userProfile: UserProfile): Map<String, UserProfile> {
        return previousState.plus(Pair(userProfile.id, userProfile.copy()))
    }
//
//    var receivedProfile: UserProfile
//        get() { return UserProfile()
//        }
//        set(value) {
//            this.store.dispatch(mapOf(
//                    "data" to "hello"
//            ))
//        }

    fun createUser(user: UserProfile, token: String? = null, inviteId: String? = null, redirect: String? = null) {
        var action: Thunk<GlobalState> = { dispatch, getState, extraArg ->
            hkClient.createUser(user, token, inviteId, redirect) {
                onSuccess {
                    receivedProfile(it)
                }
                onFailure {
                    // force log out if
//                            forceLogoutIfNecessary(error, dispatch, getState);
//                            dispatch(logError(error));
                }
            }

//            val profiles: {
//                [userId: string]: UserProfile;
//            } = {
//                [created.id]: created,
//            }
//            receivedProfile(created)
//            dispatch({type: UserTypes.RECEIVED_PROFILES, data: profiles});

            Unit
        }
        this.store.dispatch(action)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default
}