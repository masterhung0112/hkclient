package com.hungknow.services

import com.hungknow.states.GlobalState
import org.reduxkotlin.Reducer
import org.reduxkotlin.Store

interface IStateService<S> {
    open fun mainReducer(state: S, action: Any): S
    open fun setGlobalStore(store: Store<GlobalState>)
}