package com.klima7.services.common.lib.avatar

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.signature.ObjectKey
import com.klima7.services.common.R
import com.klima7.services.common.data.di.EMULATE
import com.klima7.services.common.domain.models.ProfileImage

class AvatarView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private var profileImage: ProfileImage? = null

    init {
        inflate(context, R.layout.view_avatar, this)
        refreshView()
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        val isNull = profileImage == null
        bundle.putBoolean("isNull", isNull)
        if(!isNull) {
            bundle.putLong("changeTime", profileImage?.changeTime ?: 0L)
            bundle.putString("url", profileImage?.url ?: "")
        }
        bundle.putParcelable("superState", super.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        val bundle = state as Bundle
        val isNull = bundle.getBoolean("isNull")
        if(!isNull) {
            val url = bundle.getString("url", "")
            val changeTime = bundle.getLong("changeTime")
            profileImage = ProfileImage(url, changeTime)
        }
        super.onRestoreInstanceState(bundle.getParcelable("superState"))
        refreshView()
    }

    fun setProfileImage(profileImage: ProfileImage?) {
        this.profileImage = profileImage
        refreshView()
        invalidate()
        requestLayout()
    }

    private fun refreshView() {
        val constProfileImage = profileImage ?: return

        val fixedUri = if(EMULATE) constProfileImage.url.replace("localhost", "10.0.2.2") else constProfileImage.url
        val image = findViewById<ImageView>(R.id.avatarview_image)

        Glide.with(this).clear(image)

        Glide.with(this)
            .load(fixedUri)
            .transition(DrawableTransitionOptions.withCrossFade(resources.getInteger(R.integer.avatar_fade_time)))
            .signature(ObjectKey(constProfileImage.changeTime))
            .placeholder(R.drawable.avatar_placeholder)
            .error(R.drawable.avatar_failure)
            .into(image)
    }
}
