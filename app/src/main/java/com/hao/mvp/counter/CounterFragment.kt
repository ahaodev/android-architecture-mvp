package com.hao.mvp.counter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.hao.mvp.databinding.FragmentCounterBinding
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

/**
 *@date: 2022/11/19
 *@author: 锅得铁
 *#页面实现抽象的CounterView
 */
internal class CounterFragment : Fragment(), ICounterView {

    override lateinit var presenter: ICounterPresenter

    private var loadingDialog: AlertDialog? = null

    private fun createLoadingDialog(): AlertDialog {
        val progressIndicator = CircularProgressIndicator(requireContext()).apply {
            isIndeterminate = true
        }
        return AlertDialog.Builder(requireContext())
            .setTitle("Loading")
            .setMessage("Please wait...")
            .setView(progressIndicator)
            .setCancelable(false)
            .create()
    }

    private val mBinding by lazy {
        FragmentCounterBinding.inflate(layoutInflater).apply {
            btnMinus.setOnClickListener {
                presenter.minus()
            }
            btnPlus.setOnClickListener {
                presenter.plus()
            }
        }
    }

    /**
     * Add presenter,then presenter bind to view
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Use Koin to inject presenter
        presenter = get { parametersOf(this, lifecycleScope) }
        lifecycle.addObserver(presenter)
        return mBinding.root
    }

    /**
     * Remove presenter
     */
    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(presenter)
    }

    override fun result(number: Int) {
        mBinding.tvNumber.text = "$number"
    }


    override fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = createLoadingDialog()
        }
        loadingDialog?.show()
    }

    override fun hideLoading() {
        loadingDialog?.dismiss()
    }

}