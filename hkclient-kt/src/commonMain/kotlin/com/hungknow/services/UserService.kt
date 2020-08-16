package com.hungknow.services

import com.hungknow.models.UserProfile
import com.hungknow.states.GlobalState
import org.reduxkotlin.Store
import org.reduxkotlin.Thunk

class UserService(val store: Store<GlobalState>) {

    fun createUser(user: UserProfile, token: String? = null, inviteId: String? = null, redirect: String? = null): UserProfile {
        var userProfile: UserProfile = UserProfile()
        var action: Thunk<GlobalState> = { dispatch, getState, extraArg ->
            var state = getState()
            var created = UserProfile("idfromme")
            userProfile = created
            created
        }
        this.store.dispatch(action)
        return userProfile
    }
}