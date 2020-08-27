import * as redux from 'redux'
import { createReducer } from './helpers'
import { Reducer, Action } from 'redux'

export default function configureServiceStore(
  preloadedState: any,
  appReducer: any,
  userOfflineConfig: any,
  getAppReducer: any,
  clientOptions: any
) {
  const store = redux.createStore()

  return store
}

function createDevReducer(baseState: any, ...reducers: any) {
  return enableFreezing(createReducer(baseState, ...reducers))
}

function enableFreezing(reducer: Reducer) {
  return (state: GlobalState, action: Action) => {
    const nextState = reducer(state, action)

    if (nextState !== state) {
      deepFreezeAndThrowOnMutation(nextState)
    }

    return nextState
  }
}
