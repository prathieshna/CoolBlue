package nl.coolblue.mobile.state

import nl.coolblue.mobile.actions.BaseAction
import nl.coolblue.mobile.domain.SearchResult
import org.rekotlin.StateType

data class UdfBaseState<T>(
    val state: T,
    val systemStateUpdateTracker: Map<String, BaseAction> = hashMapOf()
) : StateType

enum class ActionStatus {
    INIT, COMPLETED, ERROR
}

fun <T> getStateFlowStatusBySession(state: UdfBaseState<T>, sessionId: String): BaseAction? {
    return state.systemStateUpdateTracker[sessionId]
}

data class AppState(
    val searchResult: SearchResult? = SearchResult()
)