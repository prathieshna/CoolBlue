package nl.coolblue.mobile.actions

import android.content.Context
import nl.coolblue.mobile.domain.ApiError
import nl.coolblue.mobile.state.ActionStatus
import nl.coolblue.mobile.domain.SearchResult

sealed class GetProductsBySearch(
    baseId: String? = "",
    actionStatus: ActionStatus? = ActionStatus.INIT,
    error: ApiError? = null
) : BaseAction(baseId, actionStatus, error) {
    class Request(
        val query: String,
        val page: Int,
        val context: Context,
        actionId: String?
    ) : GetProductsBySearch(baseId = actionId)

    class Perform(
        val searchResult: SearchResult,
        val page: Int,
        actionId: String?
    ) : GetProductsBySearch(baseId = actionId)

    class Success(actionId: String?) :
        GetProductsBySearch(baseId = actionId, actionStatus = ActionStatus.COMPLETED)

    class Failure(actionError: ApiError?, actionId: String?) : GetProductsBySearch(
        baseId = actionId,
        actionStatus = ActionStatus.ERROR,
        error = actionError
    )
}