package nl.coolblue.mobile.adapters

import android.content.Context
import android.graphics.Paint
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BulletSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import nl.coolblue.mobile.R
import nl.coolblue.mobile.databinding.LayoutSearchResultItemBinding
import nl.coolblue.mobile.domain.Product
import nl.coolblue.mobile.domain.PromotionType
import nl.coolblue.mobile.state.projections.*
import nl.coolblue.mobile.store.coolBlueStore


class ProductSearchAdapter(
    private val context: Context,
    val clickHandler: (Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val searchResultItemView: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_search_result_item, viewGroup, false)
        return ViewHolder(searchResultItemView)
    }

    override fun getItemCount(): Int {
        return getProductsFromSearch(coolBlueStore.state).size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = LayoutSearchResultItemBinding.bind(itemView)
        fun setData(position: Int) {
            with(binding){
                // ADD TO CART
                ibAddToCart.setOnClickListener {
                    clickHandler(getProductFromSearch(coolBlueStore.state,position))
                }

                // PRODUCT INFO
                tvProductName.text = getProductNameByPos(coolBlueStore.state, position) ?: context.getText(R.string.not_available)
                Picasso.get().load(getProductImageByPos(coolBlueStore.state, position)).into(ivProductImage)

                //CONSUMER REVIEW SUMMARY
                rReviewAverage.rating = getReviewAverageByPos(coolBlueStore.state, position)/2 //Divided by 2 because rating is for 10 and the no of stars is 5
                tvReviewCount.text = context.resources.getQuantityString(R.plurals.review_count, getReviewCountByPos(coolBlueStore.state, position),getReviewCountByPos(coolBlueStore.state, position))

                //UNIQUE SELLING POINTS
                val uspsInBulletPoints = SpannableString(getUSPsByPos(coolBlueStore.state,position).joinToString ("\n"))
                var start = 0
                for (usp in uspsInBulletPoints.split('\n')){
                    uspsInBulletPoints.setSpan(BulletSpan(22,
                        ResourcesCompat.getColor(context.resources, R.color.bulletColour, context.theme),7), start,start + usp.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    start += (usp.length) + 1
                }
                tvUSPs.text = uspsInBulletPoints

                //SALES PRICE
                tvSalesPriceIncVat.text = String.format("%.2f", getSalesPriceIncVatByPos(coolBlueStore.state,position))

                //DICOUNTED PRICE STRIKE THROUGH
                if(isDiscountedByPos(coolBlueStore.state,position) && getListPriceIncVatByPos(coolBlueStore.state,position) != 0.0){
                    tvListPriceIncVat.visibility = View.VISIBLE
                    tvListPriceIncVat.paintFlags = tvListPriceIncVat.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    tvListPriceIncVat.text = String.format("%.2f", getListPriceIncVatByPos(coolBlueStore.state,position))
                }else{
                    tvListPriceIncVat.visibility = View.INVISIBLE
                }

                //NEXT DAY DELIVERY
                if(getNextDayDeliveryByPos(coolBlueStore.state,position)){
                    tvDeliveredTomorrow.visibility = View.VISIBLE
                }else{
                    tvDeliveredTomorrow.visibility = View.GONE
                }

                // COOLBLUERS CHOICE INFO
                if(getCoolbluesChoiceInformationTitleByPos(coolBlueStore.state,position) == null){
                    tvCoolbluesChoiceInformationTitle.visibility = View.GONE
                }else{
                    tvCoolbluesChoiceInformationTitle.visibility = View.VISIBLE
                    tvCoolbluesChoiceInformationTitle.text = getCoolbluesChoiceInformationTitleByPos(coolBlueStore.state,position)
                }

                //PROMO ICON
                when {
                    getPromoIconTypeByPos(coolBlueStore.state,position) == PromotionType.ACTION_PRICE -> {
                        iCoolbluesCoice.root.visibility = View.GONE
                        iActionPrice.root.visibility = View.VISIBLE
                        iActionPrice.root.text = getPromoIconTextByPos(coolBlueStore.state,position)
                    }
                    getPromoIconTypeByPos(coolBlueStore.state,position) == PromotionType.COOLBLUES_CHOICE -> {
                        iCoolbluesCoice.root.visibility = View.VISIBLE
                        iActionPrice.root.visibility = View.GONE
                    }
                    else -> {
                        iCoolbluesCoice.root.visibility = View.GONE
                        iActionPrice.root.visibility = View.GONE
                    }
                }
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.setData(position)
    }
}
