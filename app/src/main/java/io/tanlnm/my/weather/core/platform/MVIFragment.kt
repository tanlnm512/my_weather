package io.tanlnm.my.weather.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import io.tanlnm.my.weather.R
import io.tanlnm.my.weather.core.extension.collectIn
import io.tanlnm.my.weather.core.extension.createLoadingDialog
import io.tanlnm.my.weather.core.extension.hideKeyboard
import io.tanlnm.my.weather.core.extension.showToast

abstract class MVIFragment<VM : MVIViewModel<*, *, *>, VB : ViewBinding> constructor(
    bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : BaseFragment<VB>(bindingInflater) {
    protected abstract val viewModel: VM

    @LayoutRes
    open val loadingRes: Int? = null
        get() = field ?: R.layout.dialog_loading
    private val loadingDlg by lazy { requireContext().createLoadingDialog(loadingRes!!) }

    // region Main functions
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
    // endregion

    // region Handle error functions
    open fun noInternet() {}

    open fun showErrorMessage(msg: String) {
        context?.showToast(msg)
    }
    // endregion

    // region Fragment functions
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUI()
    }

    override fun onDestroyView() {
        if (loadingDlg.isShowing) loadingDlg.dismiss()
        requireView().hideKeyboard()

        super.onDestroyView()
    }
    // endregion
}