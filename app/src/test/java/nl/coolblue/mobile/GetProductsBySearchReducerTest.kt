package nl.coolblue.mobile

import nl.coolblue.mobile.actions.GetProductsBySearch
import nl.coolblue.mobile.domain.*
import nl.coolblue.mobile.reducers.getProductsBySearchReducer
import nl.coolblue.mobile.state.projections.*
import nl.coolblue.mobile.store.coolBlueStore
import org.junit.Assert.assertEquals
import org.junit.Test

class GetProductsBySearchReducerTest {

    private val searchResult =  SearchResult(
        products = listOf(
            Product(
                productId = 123,
                productName = "MacBook 13",
                reviewInformation = ReviewInformation(
                    reviews = null,
                    reviewSummary = ReviewSummary(
                        reviewAverage = 9.2F,
                        reviewCount = 270
                    )
                ),
                USPs = listOf(
                    "High Speed DDR4 Memory",
                    "M1 Max Chipset",
                    "SSD Storage"
                ),
                availabilityState = 1,
                salesPriceIncVat = 100.0,
                listPriceExVat = 200.0,
                listPriceIncVat = 300.0,
                productImage = "http://www.apple.com/assests/macbook.png",
                coolbluesChoiceInformationTitle = "Great Apple Product!",
                promoIcon = PromoIcon(
                    text = "Great Apple Product!",
                    type = PromotionType.COOLBLUES_CHOICE
                ),
                nextDayDelivery = true
            ),
            Product(
                productId = 123,
                productName = "MacBook AIR",
                reviewInformation = ReviewInformation(
                    reviews = null,
                    reviewSummary = ReviewSummary(
                        reviewAverage = 9.2F,
                        reviewCount = 270
                    )
                ),
                USPs = listOf(
                    "High Speed DDR4 Memory",
                    "M1 Max Chipset",
                    "SSD Storage"
                ),
                availabilityState = 1,
                salesPriceIncVat = 400.0,
                listPriceExVat = 100.0,
                listPriceIncVat = 200.0,
                productImage = "http://www.apple.com/assests/macbook.png",
                coolbluesChoiceInformationTitle = "Great Apple Product!",
                promoIcon = PromoIcon(
                    text = "aanbieding",
                    type = PromotionType.ACTION_PRICE
                ),
                nextDayDelivery = true
            )
        ), currentPage = 1, pageSize = 1, totalResults = 2, pageCount = 1
    )

    private val state = getProductsBySearchReducer(GetProductsBySearch.Perform(searchResult, 1, null), coolBlueStore.state)

    @Test
    fun checkgetSearchResult() {
        assertEquals(getSearchResult(state),searchResult)
    }

    @Test
    fun checkGetSearchCurrentPage() {
        assertEquals(getSearchCurrentPage(state),1)
    }

    @Test
    fun checkGetSearchPageCount() {
        assertEquals(getSearchPageCount(state),1)
    }

    @Test
    fun checkSearchEOL() {
        assertEquals(isSearchEOL(state),true)
    }

    @Test
    fun checkGetProductsFromSearch() {
        assertEquals(getProductsFromSearch(state),searchResult.products)
    }

    @Test
    fun checkGetProductFromSearch() {
        assertEquals(getProductFromSearch(state,0),searchResult.products?.get(0))
        assertEquals(getProductFromSearch(state,1),searchResult.products?.get(1))
    }

    @Test
    fun checkGetProductNameByPos() {
        assertEquals(getProductNameByPos(state,0),searchResult.products?.get(0)?.productName)
        assertEquals(getProductNameByPos(state,1),searchResult.products?.get(1)?.productName)
    }

    @Test
    fun checkGetCoolbluesChoiceInformationTitleByPos() {
        assertEquals(getCoolbluesChoiceInformationTitleByPos(state,0),searchResult.products?.get(0)?.coolbluesChoiceInformationTitle)
        assertEquals(getCoolbluesChoiceInformationTitleByPos(state,1),searchResult.products?.get(1)?.coolbluesChoiceInformationTitle)
    }

    @Test
    fun checkGetProductImageByPos() {
        assertEquals(getProductImageByPos(state,0),searchResult.products?.get(0)?.productImage)
        assertEquals(getProductImageByPos(state,1),searchResult.products?.get(1)?.productImage)
    }

    @Test
    fun checkGetUSPsByPos() {
        assertEquals(getUSPsByPos(state,0),searchResult.products?.get(0)?.USPs)
        assertEquals(getUSPsByPos(state,1),searchResult.products?.get(1)?.USPs)
    }

    @Test
    fun checkGetNextDayDeliveryByPos() {
        assertEquals(getNextDayDeliveryByPos(state,0),searchResult.products?.get(0)?.nextDayDelivery)
        assertEquals(getNextDayDeliveryByPos(state,1),searchResult.products?.get(1)?.nextDayDelivery)
    }

    @Test
    fun checkGetPromoIconByPos() {
        assertEquals(getPromoIconByPos(state,0),searchResult.products?.get(0)?.promoIcon)
        assertEquals(getPromoIconByPos(state,1),searchResult.products?.get(1)?.promoIcon)
    }

    @Test
    fun checkGetPromoIconTypeByPos() {
        assertEquals(getPromoIconTypeByPos(state,0),searchResult.products?.get(0)?.promoIcon?.type)
        assertEquals(getPromoIconTypeByPos(state,1),searchResult.products?.get(1)?.promoIcon?.type)
    }

    @Test
    fun checkGetPromoIconTextByPos() {
        assertEquals(getPromoIconTextByPos(state,0),searchResult.products?.get(0)?.promoIcon?.text)
        assertEquals(getPromoIconTextByPos(state,1),searchResult.products?.get(1)?.promoIcon?.text)
    }

    @Test
    fun checkIsDiscountedByPos() {
        assertEquals(isDiscountedByPos(state,0),true)
        assertEquals(isDiscountedByPos(state,1),false)
    }

    @Test
    fun checkGetSalesPriceIncVatByPos() {
        assertEquals(getSalesPriceIncVatByPos(state,0),searchResult.products?.get(0)?.salesPriceIncVat)
        assertEquals(getSalesPriceIncVatByPos(state,1),searchResult.products?.get(1)?.salesPriceIncVat)
    }

    @Test
    fun checkGetListPriceIncVatByPos() {
        assertEquals(getListPriceIncVatByPos(state,0),searchResult.products?.get(0)?.listPriceIncVat)
        assertEquals(getListPriceIncVatByPos(state,1),searchResult.products?.get(1)?.listPriceIncVat)
    }

    @Test
    fun checkGetReviewInformationByPos() {
        assertEquals(getReviewInformationByPos(state,0),searchResult.products?.get(0)?.reviewInformation)
        assertEquals(getReviewInformationByPos(state,1),searchResult.products?.get(1)?.reviewInformation)
    }

    @Test
    fun checkGetReviewSummaryByPos() {
        assertEquals(getReviewSummaryByPos(state,0),searchResult.products?.get(0)?.reviewInformation?.reviewSummary)
        assertEquals(getReviewSummaryByPos(state,1),searchResult.products?.get(1)?.reviewInformation?.reviewSummary)
    }


    @Test
    fun checkGetReviewAverageByPos() {
        assertEquals(getReviewAverageByPos(state,0),searchResult.products?.get(0)?.reviewInformation?.reviewSummary?.reviewAverage)
        assertEquals(getReviewAverageByPos(state,1),searchResult.products?.get(1)?.reviewInformation?.reviewSummary?.reviewAverage)
    }

    @Test
    fun checkGetReviewCountByPos() {
        assertEquals(getReviewCountByPos(state,0),searchResult.products?.get(0)?.reviewInformation?.reviewSummary?.reviewCount)
        assertEquals(getReviewCountByPos(state,1),searchResult.products?.get(1)?.reviewInformation?.reviewSummary?.reviewCount)
    }
}