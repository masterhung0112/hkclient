package com.hungknow.services

import com.hungknow.HkClient
import com.hungknow.models.UserProfile
import com.hungknow.states.GlobalState
import com.hungknow.states.UsersState
import kotlinx.coroutines.*
import org.reduxkotlin.*
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KFunction

interface ActionHelp<D> {
    open fun process(previousState: D): D
}

class UserService(val hkClient: HkClient) : IStateService<UsersState>, CoroutineScope {

//    lateinit var userReducer: Reducer<UsersState>
//    lateinit var actionMap: Map<String, KFunction<Any>>

    lateinit var store: Store<GlobalState>

    override fun setGlobalStore(store: Store<GlobalState>) {
        this.store = store
    }

    override fun mainReducer(s: UsersState, action: Any): UsersState {
        if (action is ActionHelp<*>) {
            when (action) {
                is ReceivedProfile -> {
                    return s.copy(profiles = action.process(s.profiles))
                }
            }
        }

        return s
    }

    data class ReceivedProfile(val userProfile: UserProfile): ActionHelp<Map<String, UserProfile>> {
        override fun process(s: Map<String, UserProfile>): Map<String, UserProfile> {
            return s.plus(Pair(userProfile.id, userProfile))
        }
    }

    fun createUser(user: UserProfile, token: String? = null, inviteId: String? = null, redirect: String? = null) {
        var action: Thunk<GlobalState> = { dispatch, getState, extraArg ->
            hkClient.createUser(user, token, inviteId, redirect) {
                onSuccess {
                    store.dispatch(ReceivedProfile(it))
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