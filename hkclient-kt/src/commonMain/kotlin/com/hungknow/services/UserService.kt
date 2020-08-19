package com.hungknow.services

import com.hungknow.HkClient
import com.hungknow.models.ClientError
import com.hungknow.models.UserProfile
import com.hungknow.states.GlobalState
import com.hungknow.states.UsersState
import com.hungknow.utils.HTTP_UNAUTHORIZED
import kotlinx.coroutines.*
import org.reduxkotlin.*
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KFunction

class UserService(private val hkClient: HkClient) : IStateService<UsersState>, CoroutineScope {

    lateinit var store: Store<GlobalState>

    override fun setGlobalStore(store: Store<GlobalState>) {
        this.store = store
    }

    companion object {
        fun profilesReducer(profiles: Map<String, UserProfile>, action: Any) = when (action) {
            is ReceivedProfile -> action.process(profiles)
            is ReceivedMe -> action.processProfile(profiles)
            else -> profiles
        }

        fun currentUserIdReducer(id: String, action: Any) = when (action) {
            is ReceivedMe -> action.processId(id)
            else -> id
        }
    }

    override fun mainReducer(s: UsersState, action: Any): UsersState {
        val profiles = profilesReducer(s.profiles, action)
        val currentUserId = currentUserIdReducer(s.currentUserId, action)
        val newState = UsersState(currentUserId, profiles)
        return if (newState.equals(s)) s else newState
    }

    class LogoutSuccess

    // Copy a profile into the profiles list
    data class ReceivedProfile(val userProfile: UserProfile) {
        fun process(s: Map<String, UserProfile>): Map<String, UserProfile> {
            return s.plus(Pair(userProfile.id, userProfile))
        }
    }

    // Copy the profile of me into the profiles list
    data class ReceivedMe(val userProfile: UserProfile) {
        fun processProfile(s: Map<String, UserProfile>): Map<String, UserProfile> {
            val oldUser = s[userProfile.id]
            var user = userProfile.copy()
            if (oldUser != null) {
//                user.terms_of_service_id = oldUser.terms_of_service_id;
//                user.terms_of_service_create_at = oldUser.terms_of_service_create_at;
            }

            return s.plus(Pair(userProfile.id, user))
        }

        fun processId(s: String): String {
            return userProfile.id
        }
    }

    fun forceLogoutIfNecessary(err: ClientError, dispatch: Dispatcher, getState: GetState<GlobalState>) {
        val currentUserId = getState().entities.users.currentUserId

        if (err.status_code != null && err.status_code === HTTP_UNAUTHORIZED && err.url != null && err.url!!.indexOf("/login") === -1 && currentUserId.isNullOrBlank()) {
            hkClient.token = ""
            dispatch(LogoutSuccess())
        }
    }

    fun createUser(user: UserProfile, token: String? = null, inviteId: String? = null, redirect: String? = null) {
        var action: Thunk<GlobalState> = { dispatch, getState, extraArg ->
            hkClient.createUser(user, token, inviteId, redirect) {
                onSuccess {
                    dispatch(ReceivedProfile(it))
                }
                onFailure {
                    if (it is ClientError) {
                        // force log out if necessary
                        forceLogoutIfNecessary(it, dispatch, getState)
                    }
//                            dispatch(logError(error));
                }
            }
        }
        this.store.dispatch(action)
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default
}