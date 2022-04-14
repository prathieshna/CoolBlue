package nl.coolblue.mobile.middleware

import nl.coolblue.mobile.actions.GetProductsBySearch
import nl.coolblue.mobile.domain.SearchResult
import nl.coolblue.mobile.domain.ApiError
import nl.coolblue.mobile.middleware.utils.parseError
import nl.coolblue.mobile.R
import nl.coolblue.mobile.services.apiService
import org.rekotlin.DispatchFunction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getProductsBySearch(dispatch: DispatchFunction, action: GetProductsBySearch.Request) {
    val service = apiService()

    val call = service.getProductsBySearch(
        query = action.query,
        page = action.page
    )

    call.enqueue(object : Callback<SearchResult?> {
        override fun onResponse(
            call: Call<SearchResult?>,
            response: Response<SearchResult?>
        ) {
            response.let {
                if (response.code() == 200) {
                    dispatch(
                        GetProductsBySearch.Perform(
                            searchResult = response.body()?: SearchResult(),
                            page = action.page,
                            actionId = action.getId()
                        )
                    )
                } else {
                    throwError(parseError(response.errorBody(), response.code(), action.context))
                }
            }
        }

        override fun onFailure(call: Call<SearchResult?>, t: Throwable) {
            val apiError = ApiError(
                500,
                t.message ?: action.context.getString(R.string.unexpected_error)
            )
            throwError(apiError)
        }

        fun throwError(error: ApiError) {
            dispatch(GetProductsBySearch.Failure(error, action.getId()))
        }
    })
}