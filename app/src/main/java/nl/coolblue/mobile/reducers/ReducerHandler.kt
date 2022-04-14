package nl.coolblue.mobile.reducers

import nl.coolblue.mobile.state.AppState
import nl.coolblue.mobile.state.UdfBaseState
import nl.coolblue.mobile.actions.GetProductsBySearch
import org.rekotlin.Action

val reducerHandler: ReducerHandler<AppState> = { action: Action, appState: UdfBaseState<AppState> ->
    when (action) {
        is GetProductsBySearch -> {
            getProductsBySearchReducer(action, appState)
        }
        else -> appState
    }
}
