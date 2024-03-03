package io.tanlnm.my.weather.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import io.tanlnm.my.weather.R
import io.tanlnm.my.weather.core.extension.collectIn
import io.tanlnm.my.weather.core.extension.createLoadingDialog
import io.tanlnm.my.weather.core.extension.showToast

abstract class MVIActivity<VM : MVIViewModel<*, *, *>, VB : ViewBinding> constructor(
    bindingInflater: (LayoutInflater) -> VB
) : BaseActivity<VB>(bindingInflater) {
    protected abstract val viewModel: VM

    private val loadingDlg by lazy { createLoadingDialog(R.layout.dialog_loading) }

    open fun subscribeUI() {
        viewModel.layoutIndicator.collectIn(this) {
            when (it) {
                is MVIViewModel.LayoutIndicator.Loading -> {
                    if (it.isLoading) loadingDlg.show() else loadingDlg.dismiss()
                }

                is MVIViewModel.LayoutIndicator.NoInternetConnection -> noInternet()

                is MVIViewModel.LayoutIndicator.ShowErrorMessage -> showErrorMessage(it.msg)
            }
        }
    }
    open suspend fun handleState(state: IState) {}
    open suspend fun handleEffect(effect: IEffect) {}

    // region Handle error functions
    open fun noInternet() {}

    open fun showErrorMessage(msg: String) {
        showToast(msg)
    }
    // endregion

    open fun showLoading(isShow: Boolean) {
        if (isShow) loadingDlg.show() else loadingDlg.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeUI()
    }

    override fun onDestroy() {
        if (loadingDlg.isShowing) loadingDlg.dismiss()
        super.onDestroy()
    }
}