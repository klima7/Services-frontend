package com.klima7.services.common.components.comments

import com.klima7.services.common.base.BaseActivity

class CommentsActivity: BaseActivity() {
    override fun fragment() = CommentsToolbarFragment().apply {
        arguments = intent.extras
    }
}