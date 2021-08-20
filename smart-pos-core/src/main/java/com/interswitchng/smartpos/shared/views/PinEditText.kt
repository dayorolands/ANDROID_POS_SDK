package com.interswitchng.smartpos.shared.views

import android.annotation.TargetApi
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.interswitchng.smartpos.R

class PinEditText : EditText {

    private var mSpace = 24f // 24 dp by default, space between the lines
    private var mCharSize = 0f
    private var mNumChars = 4f
    private var mLineSpacing = 8f // 8dp by default, height of the text from our lines
    private var mMaxLength = 4
    private var isPassword = false

    private var mClickListener: View.OnClickListener? = null

    private var mLineStroke = 1f //1dp by default
    private var mLineStrokeSelected = 2f //2dp by default
    private var mLinesPaint: Paint? = null
    private var mStates = arrayOf(intArrayOf(android.R.attr.state_selected), // selected
            intArrayOf(android.R.attr.state_focused), // focused
            intArrayOf(-android.R.attr.state_focused))// unfocused

    private var mColors = intArrayOf(Color.GREEN, Color.BLACK, Color.GRAY)

    private var mColorStates = ColorStateList(mStates, mColors)

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        val multi = context.resources.displayMetrics.density
        mLineStroke *= multi
        mLineStrokeSelected *= multi
        mLinesPaint = Paint(paint)
        mLinesPaint!!.strokeWidth = mLineStroke
        if (!isInEditMode) {
            val outValue = TypedValue()
            context.theme.resolveAttribute(R.attr.colorControlActivated, outValue, true)
            val colorActivated = outValue.data
            mColors[0] = colorActivated

            context.theme.resolveAttribute(R.attr.colorPrimaryDark,outValue, true)
            val colorDark = outValue.data
            mColors[1] = colorDark

            context.theme.resolveAttribute(R.attr.colorControlHighlight,outValue, true)
            val colorHighlight = outValue.data
            mColors[2] = colorHighlight
        }

        setBackgroundResource(0)
        mSpace *= multi // convert to pixels for our density
        mLineSpacing *= multi // convert to pixels for our density
        mMaxLength = attrs.getAttributeIntValue(XML_NAMESPACE_ANDROID, "maxLength", 4)
        mNumChars = mMaxLength.toFloat()

        val inputType = attrs.getAttributeIntValue(XML_NAMESPACE_ANDROID, "inputType", -1)
        val numberPassword =  InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
        val textPassword =  InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        isPassword = inputType == textPassword || inputType == numberPassword



        // Disable copy paste
        super.setCustomSelectionActionModeCallback(object : ActionMode.Callback {
            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode) {}

            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                return false
            }
        })

        // When tapped, move cursor to end of text.
        super.setOnClickListener { v ->
            setSelection(text.length)
            mClickListener?.onClick(v)
        }

    }

    override fun setOnClickListener(l: View.OnClickListener?) {
        mClickListener = l
    }

    override fun setCustomSelectionActionModeCallback(actionModeCallback: ActionMode.Callback) {
        throw RuntimeException("setCustomSelectionActionModeCallback() not supported.")
    }

    override fun onDraw(canvas: Canvas) {
        //super.onDraw(canvas);
        val availableWidth = width - paddingRight - paddingLeft

        mCharSize = if (mSpace < 0) {
            availableWidth / (mNumChars * 2 - 1)
        } else {
            (availableWidth - mSpace * (mNumChars - 1)) / mNumChars
        }

        var startX = paddingLeft
        val bottom = height - paddingBottom

        //Text Width
        val textLength = text.length
        val textWidths = FloatArray(textLength)
        paint.getTextWidths(text, 0, textLength, textWidths)
        paint.color = textColors.defaultColor

        for (i in 0 until mNumChars.toInt()) {
            // update indicator color for current input
            updateColorForLines(i == textLength)
            canvas.drawLine(startX.toFloat(), bottom.toFloat(), startX + mCharSize, bottom.toFloat(), mLinesPaint!!)

            if (textLength > i) {
                val middle = startX + mCharSize / 2
                val textCopy = if (isPassword) encrypt() else text
                canvas.drawText(textCopy, i, i + 1, middle - textWidths[0] / 2, bottom - mLineSpacing, paint)
            }

            startX += if (mSpace < 0) {
                (mCharSize * 2).toInt()
            } else {
                (mCharSize + mSpace).toInt()
            }
        }
    }


    private fun getColorForState(vararg states: Int): Int {
        return mColorStates.getColorForState(states, Color.GRAY)
    }

    /**
     * @param next Is the current char the next character to be input?
     */
    private fun updateColorForLines(next: Boolean) {
        if (isFocused) {
            mLinesPaint!!.strokeWidth = mLineStrokeSelected
            mLinesPaint!!.color = getColorForState(android.R.attr.state_focused)
            if (next) {
                mLinesPaint!!.color = getColorForState(android.R.attr.state_selected)
            }
        } else {
            mLinesPaint!!.strokeWidth = mLineStroke
            mLinesPaint!!.color = getColorForState(-android.R.attr.state_focused)
        }
    }

    private fun encrypt(): String {
        var encrypted = ""
        for (i in 1..text.length) encrypted += "*"
        return encrypted
    }

    companion object {
        const val XML_NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android"
    }
}