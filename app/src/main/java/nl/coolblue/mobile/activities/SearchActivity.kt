package nl.coolblue.mobile.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nl.coolblue.mobile.R
import nl.coolblue.mobile.actions.BaseAction
import nl.coolblue.mobile.actions.GetProductsBySearch
import nl.coolblue.mobile.adapters.ProductSearchAdapter
import nl.coolblue.mobile.databinding.ActivitySearchBinding
import nl.coolblue.mobile.state.AppState
import nl.coolblue.mobile.state.UdfBaseState
import nl.coolblue.mobile.state.projections.getProductsFromSearch
import nl.coolblue.mobile.state.projections.getSearchCurrentPage
import nl.coolblue.mobile.state.projections.isSearchEOL
import nl.coolblue.mobile.store.coolBlueStore


class SearchActivity : BaseActivity() {
    private val binding by viewBinding(ActivitySearchBinding::inflate)
    private lateinit var adapter: ProductSearchAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var isLoading: Boolean = false

    override fun onStateUpdate(state: UdfBaseState<AppState>, action: BaseAction): Boolean {
        return when (action) {
            is GetProductsBySearch -> {
                isLoading = false

                with(binding){
                    adapter.notifyItemRangeChanged(0,getProductsFromSearch(state).size)
                    rlSearchResults.visibility = View.VISIBLE
                }

                true
            }
            else -> {
                false
            }
        }
    }

    override fun onRawStateUpdate(state: UdfBaseState<AppState>) {
    }

    override fun onError(action: BaseAction) {
        when (action) {
            is GetProductsBySearch -> {
                alertDialog.showDialog(action.error?.message ?: getString(R.string.not_available))
                isLoading = false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        with(binding){
            ivClear.setOnClickListener {
                etSearch.text.clear()
                ivClear.visibility = View.GONE
            }
        }

        setUpSearchTextWatcher()
        setUpSearchResultsGrid()

    }

    private fun setUpSearchResultsGrid() {
       with(binding){

           linearLayoutManager = LinearLayoutManager(this@SearchActivity)
           linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
           rvSearchResults.layoutManager = linearLayoutManager
           adapter = ProductSearchAdapter(this@SearchActivity) { selectedItem ->
               Toast.makeText(this@SearchActivity,selectedItem.productName + " added to cart!",Toast.LENGTH_LONG).show()
           }
           rvSearchResults.adapter = adapter
           rvSearchResults.addOnScrollListener(object : RecyclerView.OnScrollListener() {
               override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                   val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                   if (!isLoading && lastVisibleItemPosition + 5 > linearLayoutManager.itemCount && getProductsFromSearch(coolBlueStore.state).isNotEmpty() && !isSearchEOL(coolBlueStore.state)) {
                       isLoading = true
                       dispatchAction(
                           GetProductsBySearch.Request(
                               query = etSearch.text.toString(),
                               page = getSearchCurrentPage(coolBlueStore.state) + 1,
                               context = this@SearchActivity,
                               actionId = getActionId()
                           )
                       )
                   }
               }
           })
       }
    }

    private fun setUpSearchTextWatcher() {
        with(binding){
            etSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(currentText: Editable?) {

                }

                override fun beforeTextChanged(
                    currentText: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(
                    currentText: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    if(currentText?.isEmpty() == true){
                        ivClear.visibility = View.GONE
                    }else{
                        ivClear.visibility = View.VISIBLE
                    }
                }
            })

            etSearch.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    isLoading = true
                    dispatchAction(
                        GetProductsBySearch.Request(
                            query = etSearch.text.toString(),
                            page = 0,
                            context = this@SearchActivity,
                            actionId = getActionId()
                        )
                    )
                    return@OnEditorActionListener true
                }
                false
            })

        }

    }

}
