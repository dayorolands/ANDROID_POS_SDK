package com.interswitchng.smartpos.shared.utilities

import android.os.Bundle
import android.view.View
import com.interswitchng.smartpos.R
import com.interswitchng.smartpos.shared.activities.BaseBottomSheetDialog
import kotlinx.android.synthetic.main.isw_sheet_layout_currency_option.*

class CurrencyDialog constructor(
    private val optionClickListener: SingleArgsClickListener<Int>
): BaseBottomSheetDialog(){
    override val layoutId: Int
        get() = R.layout.isw_sheet_layout_currency_option

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        isw_naira.setOnClickListener {
            optionClickListener.invoke(0)
            dismiss()
        }
        isw_dollar.setOnClickListener {
            optionClickListener.invoke(1)
            dismiss()
        }
    }
}