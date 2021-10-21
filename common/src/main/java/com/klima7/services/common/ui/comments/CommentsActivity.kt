package com.klima7.services.common.ui.comments

import android.util.Log
import com.klima7.services.common.ui.base.BaseActivity

class CommentsActivity: BaseActivity() {
    override fun fragment() = CommentsToolbarFragment().apply {
        Log.i("Hello", "Arguments inside activity: ${intent.extras}")
        arguments = intent.extras
    }
}