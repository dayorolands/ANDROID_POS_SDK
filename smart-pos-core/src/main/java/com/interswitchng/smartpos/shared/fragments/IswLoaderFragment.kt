package com.interswitchng.smartpos.shared.fragments

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.interswitchng.smartpos.R

internal class IswLoaderFragment: Fragment() {

    // loader fields
    private lateinit var loaderText: TextView
    private lateinit var loadingViews: MutableList<View>

    private lateinit var retryBtn: Button
    private lateinit var cancelBtn: Button
    private lateinit var confirmText: TextView
    private lateinit var confirmationViews: List<View>

    private lateinit var state: LoadingState



    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstance: Bundle?): View? {
        return inflater.inflate(R.layout.isw_fragment_loader, parent, false)
    }


    override fun onViewCreated(view: View, savedInstance: Bundle?) {

        loaderText = view.findViewById(R.id.tvLoader)
        val loaderProgress = view.findViewById<ProgressBar>(R.id.loader)
        // add views to container
        loadingViews = mutableListOf(loaderText, loaderProgress)


        // get confirmation views confirmation fields
        cancelBtn = view.findViewById(R.id.confirmationCancel)
        retryBtn = view.findViewById(R.id.confirmationRetry)
        confirmText = view.findViewById(R.id.confirmationText)
        val confirmImage = view.findViewById<ImageView>(R.id.warningIcon)


        // add views to container
        confirmationViews = mutableListOf(cancelBtn, retryBtn, confirmImage, confirmText)

        // render state
        setState(state)
    }


    fun setState(state: LoadingState) {
        // copy initial state
        this.state = state

        // if view is not attached yet return
        if (!isAdded) return

        // handle state
        when(state) {
            is LoadingState.Loading -> showLoader(state.message)
            is LoadingState.ErrorLoading -> showError(state.errorMessage, state.cancelAction, state.retryAction)
        }
    }


    private fun showError(errorMsg: String, cancelAction: () -> Unit, retryAction: () -> Unit) {
        confirmText.text = errorMsg
        retryBtn.isClickable = true

        // set retry click listener
        retryBtn.setOnClickListener { view ->
            view.isClickable = false
            retryAction()
        }

        // attach cancel click listener
        cancelBtn.setOnClickListener { view ->
            view.isClickable = false
            cancelAction()
        }


        // hide all loading views
        for (loadingView in loadingViews)
            loadingView.visibility = View.GONE

        // show all confirmation fields
        for (confirmView in confirmationViews)
            confirmView.visibility = View.VISIBLE
    }

    private fun showLoader(message: String) {
        // show loader fields
        loaderText.text = message

        // show all loading vies
        for (loadingView in loadingViews)
            loadingView.visibility = View.VISIBLE

        // hide all confirmation fields
        for (confirmView in confirmationViews)
            confirmView.visibility = View.GONE
    }

    internal sealed class LoadingState {
        data class Loading(val message: String): LoadingState()
        data class ErrorLoading(val errorMessage: String, val cancelAction: () -> Unit, val retryAction: () -> Unit): LoadingState()
    }

}