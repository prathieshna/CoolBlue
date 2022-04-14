package nl.coolblue.mobile.store

import nl.coolblue.mobile.middleware.getNetworkMiddleware
import nl.coolblue.mobile.middleware.middlewareHandler
import nl.coolblue.mobile.reducers.getAppReducer
import nl.coolblue.mobile.reducers.reducerHandler
import nl.coolblue.mobile.state.AppState
import org.rekotlin.Store


val coolBlueStore = Store(
    reducer = getAppReducer(AppState(), reducerHandler),
    state = null,
    middleware = listOf(getNetworkMiddleware(middlewareHandler))
)
