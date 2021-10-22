package com.klima7.services.common.components.comments

import com.klima7.services.common.platform.BaseActivity

class CommentsActivity: BaseActivity() {
    override fun fragment() = CommentsToolbarFragment().apply {
        arguments = intent.extras
    }
}