package com.klima7.services.common.lib.avatar

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.klima7.services.common.R
import com.klima7.services.common.data.di.EMULATE
import de.hdodenhof.circleimageview.CircleImageView

class AvatarView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var uri: String = ""
    private var signature: Long = System.currentTimeMillis()

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Avatar,
            0, 0).apply {

            try {
                uri = getText(R.styleable.Avatar_uri)?.toString() ?: ""
            } finally {
                inflate(context, R.layout.view_avatar, this@AvatarView)
                recycle()

                refreshView()
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putLong("signature", signature!!)
        bundle.putParcelable("superState", super.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val bundle = state as Bundle
        signature = bundle.getLong("signature")
        super.onRestoreInstanceState(bundle.getParcelable("superState"))
        refreshView()
    }

    fun setUri(uri: String) {
        this.signature = System.currentTimeMillis()
        this.uri = uri
        refreshView()
        invalidate()
        requestLayout()
    }

    private fun refreshView() {
        val fixedUri = if(EMULATE) uri.replace("localhost", "10.0.2.2") else uri
        val image = findViewById<CircleImageView>(R.id.avatar_image)
        Glide.with(context).clear(image)

        Glide.with(this)
            .load(fixedUri)
            .signature(ObjectKey(signature))
            .placeholder(R.drawable.image_profile_default)
            .error(R.drawable.image_profile_default)
            .into(image)
    }
}
