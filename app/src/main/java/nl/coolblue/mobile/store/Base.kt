package nl.coolblue.mobile.store
import nl.coolblue.mobile.actions.BaseAction
import nl.coolblue.mobile.actions.RemoveStateStatus
import nl.coolblue.mobile.state.ActionStatus
import nl.coolblue.mobile.state.UdfBaseState
import nl.coolblue.mobile.state.getStateFlowStatusBySession
import org.rekotlin.Store

import org.rekotlin.StoreSubscriber
import java.util.*

interface Base<T> : StoreSubscriber<UdfBaseState<T>> {
    val appStore: Store<UdfBaseState<T>>
    var actionSessionIds: ArrayList<String>

    fun getActionId(): String {
        val actionId = UUID.randomUUID().toString()
        actionSessionIds.add(actionId)
        return actionId
    }

    fun dispatchAction(action: BaseAction, showLoader: Boolean = true) {
        if (showLoader)
            showLoader()
        appStore.dispatch(action)
    }

    fun showLoader()

    fun hideLoader()

    override fun newState(state: UdfBaseState<T>) {
        this.onRawStateUpdate(state)
        val ids: List<String> = this.actionSessionIds.toList()
        ids.forEach { updateActivity(state, it) }
    }

    private fun updateActivity(state: UdfBaseState<T>, actionId: String) {
        getStateFlowStatusBySession(state, actionId)?.let { action ->
            if (action.status == ActionStatus.COMPLETED) {
                appStore.dispatch(RemoveStateStatus.Perform(actionId, getActionId()))
                if (onStateUpdate(state, action))
                    hideLoader()
            } else if (action.status == ActionStatus.ERROR) {
                appStore.dispatch(RemoveStateStatus.Perform(actionId, getActionId()))
                onError(action)
                hideLoader()
            }
        }
    }

    fun onStateUpdate(state: UdfBaseState<T>, action: BaseAction): Boolean
    fun onRawStateUpdate(state: UdfBaseState<T>)
    fun onError(action: BaseAction)
}