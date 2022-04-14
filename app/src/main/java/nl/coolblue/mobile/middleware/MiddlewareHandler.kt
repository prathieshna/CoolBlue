package nl.coolblue.mobile.middleware

import nl.coolblue.mobile.actions.GetProductsBySearch
import org.rekotlin.Action
import org.rekotlin.DispatchFunction

val middlewareHandler: MiddleWareHandler = { dispatch: DispatchFunction, action: Action ->
    when (action) {
        is GetProductsBySearch.Request -> {
            getProductsBySearch(dispatch, action)
        }
    }
}