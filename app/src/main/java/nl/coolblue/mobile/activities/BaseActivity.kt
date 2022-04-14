package nl.coolblue.mobile.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import nl.coolblue.mobile.dialogs.AlertDialog
import nl.coolblue.mobile.dialogs.LoaderDialog
import nl.coolblue.mobile.state.AppState
import nl.coolblue.mobile.state.UdfBaseState
import nl.coolblue.mobile.store.Base
import nl.coolblue.mobile.store.coolBlueStore
import org.rekotlin.Store
import java.util.*

abstract class BaseActivity : AppCompatActivity(), Base<AppState> {
    override val appStore: Store<UdfBaseState<AppState>> = coolBlueStore
    private lateinit var loaderDialog: LoaderDialog
    lateinit var alertDialog: AlertDialog
    override var actionSessionIds: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loaderDialog = LoaderDialog(this)
        alertDialog = AlertDialog(this)
    }

    override fun hideLoader() {
        loaderDialog.hideDialog()
    }

    override fun showLoader() {
        loaderDialog.showDialog()
    }

    override fun onStart() {
        super.onStart()
        appStore.subscribe(this)
    }

    override fun onStop() {
        super.onStop()
        appStore.unsubscribe(this)
    }
}

inline fun <T : ViewBinding> ComponentActivity.viewBinding(crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }
