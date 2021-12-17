package com.klima7.services.common.components.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.klima7.services.common.R


class ConstrainedColumnView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var content: LinearLayout? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_constrained_column, this)
        content = findViewById(R.id.viewconstrainedcolumn_content)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if(content == null) {
            super.addView(child, index, params)
        }
        else {
            content?.addView(child, index, params)
        }
    }
}
