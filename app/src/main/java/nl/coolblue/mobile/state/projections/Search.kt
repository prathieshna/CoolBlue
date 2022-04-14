package nl.coolblue.mobile.state.projections

import nl.coolblue.mobile.domain.*
import nl.coolblue.mobile.state.AppState
import nl.coolblue.mobile.state.UdfBaseState

val getSearchResult: (state: UdfBaseState<AppState>) -> SearchResult = { state -> state.state.searchResult ?: SearchResult()}
val getSearchCurrentPage: (state: UdfBaseState<AppState>) -> Int = { state -> getSearchResult(state).currentPage ?: 0}
val getSearchPageCount: (state: UdfBaseState<AppState>) -> Int = { state -> getSearchResult(state).pageCount ?: 0}

val isSearchEOL:  (state: UdfBaseState<AppState>) -> Boolean = { state -> getSearchCurrentPage(state) == getSearchPageCount(state)}

val getProductsFromSearch: (state: UdfBaseState<AppState>) -> List<Product> = { state -> getSearchResult(state).products?.map { it ?: Product() } ?: listOf()}
val getProductFromSearch: (state: UdfBaseState<AppState>, position: Int) -> Product = { state, position -> getProductsFromSearch(state)[position]}

val getProductNameByPos: (state: UdfBaseState<AppState>, position: Int) -> String? = { state, position -> getProductFromSearch(state,position).productName}
val getCoolbluesChoiceInformationTitleByPos: (state: UdfBaseState<AppState>, position: Int) -> String? = { state, position -> getProductFromSearch(state,position).coolbluesChoiceInformationTitle}
val getProductImageByPos: (state: UdfBaseState<AppState>, position: Int) -> String? = { state, position -> getProductFromSearch(state,position).productImage}
val getUSPsByPos: (state: UdfBaseState<AppState>, position: Int) -> List<String> = { state, position -> getProductFromSearch(state,position).USPs?.map { it ?: "" } ?: listOf()}
val getNextDayDeliveryByPos: (state: UdfBaseState<AppState>, position: Int) -> Boolean = { state, position -> getProductFromSearch(state,position).nextDayDelivery ?: false}

val getPromoIconByPos: (state: UdfBaseState<AppState>, position: Int) -> PromoIcon = { state, position -> getProductFromSearch(state,position).promoIcon ?: PromoIcon() }
val getPromoIconTypeByPos: (state: UdfBaseState<AppState>, position: Int) -> PromotionType? = { state, position -> getPromoIconByPos(state,position).type}
val getPromoIconTextByPos: (state: UdfBaseState<AppState>, position: Int) -> String? = { state, position -> getPromoIconByPos(state,position).text}

val isDiscountedByPos : (state: UdfBaseState<AppState>, position: Int) -> Boolean = { state, position -> getSalesPriceIncVatByPos(state,position) < getListPriceIncVatByPos(state,position)}
val getSalesPriceIncVatByPos: (state: UdfBaseState<AppState>, position: Int) -> Double = { state, position -> getProductFromSearch(state,position).salesPriceIncVat ?: 0.0 }
val getListPriceIncVatByPos: (state: UdfBaseState<AppState>, position: Int) -> Double = { state, position -> getProductFromSearch(state,position).listPriceIncVat ?: 0.0}

val getReviewInformationByPos: (state: UdfBaseState<AppState>, position: Int) -> ReviewInformation = { state, position -> getProductFromSearch(state,position).reviewInformation ?: ReviewInformation()}
val getReviewSummaryByPos: (state: UdfBaseState<AppState>, position: Int) -> ReviewSummary = { state, position -> getReviewInformationByPos(state,position).reviewSummary ?: ReviewSummary()}
val getReviewAverageByPos: (state: UdfBaseState<AppState>, position: Int) -> Float = { state, position -> getReviewSummaryByPos(state,position).reviewAverage ?: 0.0F}
val getReviewCountByPos: (state: UdfBaseState<AppState>, position: Int) -> Int = { state, position -> getReviewSummaryByPos(state,position).reviewCount ?: 0}


