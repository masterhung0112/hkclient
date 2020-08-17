package com.hungknow.services

import com.hungknow.HkClient
import com.hungknow.models.UserProfile
import com.hungknow.states.GlobalState
import com.hungknow.states.UsersState
import org.reduxkotlin.*
import kotlin.reflect.KFunction

class UserService(val store: Store<GlobalState>, val hkClient: HkClient) {

    lateinit var userReducer: Reducer<UsersState>
    lateinit var actionMap: Map<String, KFunction<Any>>

    constructor() {
        // Register the reducers
        userReducer = combineReducers(::exampleReducer, ::exampleReducer1)
        actionMap = mapOf(
                "hello" to ::exampleReducer
        )
        var f = actionMap.get("hello")
        f!!.call("hello")
    }

    fun exampleReducer(previousState: UsersState, action: Any): UsersState {
        var f = actionMap.get("hello")
        f!!.call(previousState, action)
        return previousState
    }

    fun exampleReducer1(previousState: UsersState, action: Any): UsersState {
        return previousState
    }

    fun createReducersCallable() {

    }

    fun receivedProfile(previousState: Map<String, UserProfile>, userProfile: UserProfile): Map<String, UserProfile> {
        return previousState.plus(Pair(userProfile.id, userProfile.copy()))
    }

    var receivedProfile: UserProfile
        get() { return UserProfile()
        }
        set(value) {
            this.store.dispatch(mapOf(
                    "data" to "hello"
            ))
        }

    fun createUser(user: UserProfile, token: String? = null, inviteId: String? = null, redirect: String? = null): UserProfile {
        var userProfile: UserProfile = UserProfile()
        var action: Thunk<GlobalState> = { dispatch, getState, extraArg ->
            var state = getState()
            var created: UserProfile? = null

            try {
                created = this.hkClient.createUser(user, token, inviteId, redirect)
            } catch (error: Exception) {
//                forceLogoutIfNecessary(error, dispatch, getState);
//                dispatch(logError(error));
                throw error
            }

            val profiles: {
                [userId: string]: UserProfile;
            } = {
                [created.id]: created,
            }
//            dispatch({type: UserTypes.RECEIVED_PROFILES, data: profiles});

            userProfile = created
            created
        }
        this.store.dispatch(action)
        return userProfile
    }
}