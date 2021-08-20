package com.interswitchng.smartpos.shared.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.interswitchng.smartpos.R

abstract class BaseBottomSheetDialog : BottomSheetDialogFragment() {

    protected lateinit var rootView: View

    protected val sheetContext by lazy { requireParentFragment().requireContext() }

    abstract val layoutId: Int

    override fun getTheme(): Int = R.style.IswBottomSheet

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(layoutId, container, false)
        return rootView
    }
}

