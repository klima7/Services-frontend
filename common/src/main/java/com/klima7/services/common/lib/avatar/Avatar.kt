package com.klima7.services.common.lib.avatar

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.klima7.services.common.R
import com.klima7.services.common.data.di.EMULATE
import de.hdodenhof.circleimageview.CircleImageView

class Avatar(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var uri: String? = null

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Avatar,
            0, 0).apply {

            try {
                uri = getText(R.styleable.Avatar_uri)?.toString()
            } finally {
                inflate(context, R.layout.view_avatar, this@Avatar)
                refreshView()
                recycle()
            }
        }
    }

    fun setUri(uri: String) {
        this.uri = uri
        refreshView()
        invalidate()
        requestLayout()
    }

    private fun refreshView() {
        val fixedUri = if(EMULATE) uri?.replace("localhost", "10.0.2.2") else uri
        val image = findViewById<CircleImageView>(R.id.avatar_image)
        Log.i("Hello", "Avatar! Setting image to '$fixedUri'")
        Glide.with(context).clear(image)
        Glide.with(this)
            .load(fixedUri)
            .signature(ObjectKey(System.currentTimeMillis()))
            .placeholder(R.drawable.image_profile_default)
            .error(R.drawable.image_profile_default)
            .into(image)
    }
}