package co.nz.tsb.interview.bankrecmatchmaker.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Checkable
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatCheckBox
import co.nz.tsb.interview.bankrecmatchmaker.R

class CheckedListItem :
    LinearLayout,
    Checkable {
    private var checkBox: AppCompatCheckBox? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr,
    ) {
        init(context)
    }

    private fun init(context: Context) {
        val layoutInflater = LayoutInflater.from(context)
        orientation = HORIZONTAL
        checkBox =
            layoutInflater.inflate(R.layout.list_item_checkbox, this, false) as AppCompatCheckBox

        checkBox?.isClickable = false
        checkBox?.isFocusable = false
        addView(checkBox, 0)
    }

    override fun setChecked(checked: Boolean) {
        checkBox?.isChecked = checked
    }

    override fun isChecked(): Boolean = checkBox?.isChecked ?: false

    override fun toggle() {
        checkBox?.toggle()
    }
}
