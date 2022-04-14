package nl.coolblue.mobile.reducers

import nl.coolblue.mobile.actions.BaseAction
import nl.coolblue.mobile.actions.RemoveStateStatus
import nl.coolblue.mobile.state.UdfBaseState
import org.rekotlin.Action
import org.rekotlin.Reducer

typealias ReducerHandler<T> = (Action, UdfBaseState<T>) -> UdfBaseState<T>

fun <T> updateActionsStateStatus(
    state: UdfBaseState<T>,
    actionId: String?,
    action: BaseAction,
    localState: T? = null
): UdfBaseState<T> {
    var stateToReturn = state
    if (localState != null) {
        stateToReturn = state.copy(state = localState)
    }
    if (actionId != null) {
        val statusMap = stateToReturn.systemStateUpdateTracker
            .toMutableMap()
            .filterKeys { it != actionId }
            .toMutableMap()
        statusMap[actionId] = action
        return stateToReturn.copy(systemStateUpdateTracker = statusMap.toMap())
    }
    return stateToReturn
}

fun <T> getAppReducer(stateInstance: T, handler: ReducerHandler<T>): Reducer<UdfBaseState<T>> {
    return { action, state ->
        when (action) {
            is RemoveStateStatus.Perform -> {
                val statusMap = state!!.systemStateUpdateTracker
                    .toMutableMap()
                    .filterKeys { it != action.uuid }
                state.copy(systemStateUpdateTracker = statusMap)
            }
            else -> handler(action, state ?: UdfBaseState(stateInstance))
        }
    }
}

