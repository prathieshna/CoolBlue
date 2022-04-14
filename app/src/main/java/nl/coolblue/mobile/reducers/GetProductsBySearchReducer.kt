package nl.coolblue.mobile.reducers

import nl.coolblue.mobile.state.AppState
import nl.coolblue.mobile.state.UdfBaseState
import nl.coolblue.mobile.state.projections.getProductsFromSearch
import nl.coolblue.mobile.actions.GetProductsBySearch
import nl.coolblue.mobile.domain.Product
import org.rekotlin.Action

fun getProductsBySearchReducer(
    action: Action,
    state: UdfBaseState<AppState>
): UdfBaseState<AppState> {
    if (action as GetProductsBySearch is GetProductsBySearch.Perform) {
        val data = action as GetProductsBySearch.Perform
        var results: MutableList<Product?>? = getProductsFromSearch(state).toMutableList()

        if (data.page != 1) {
            results?.addAll(data.searchResult.products?.map { it } ?: listOf())
        } else {
            results = data.searchResult.products?.map { it }?.toMutableList() ?: mutableListOf()
        }

        val newAppState =
            state.state.copy(searchResult = action.searchResult.copy(products = results))
        return updateActionsStateStatus(
            state,
            action.getId(),
            GetProductsBySearch.Success(action.getId()),
            newAppState
        )
    } else if (action is GetProductsBySearch.Failure) {
        return updateActionsStateStatus(
            state, action.getId(), GetProductsBySearch.Failure(action.error, action.getId())
        )
    }
    return state
}