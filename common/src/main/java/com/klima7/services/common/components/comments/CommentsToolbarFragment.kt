package com.klima7.services.common.components.comments

import androidx.fragment.app.Fragment
import com.klima7.services.common.components.toolbar.ToolbarFragment

class CommentsToolbarFragment: ToolbarFragment(
    back = true,
) {

    override fun fragment(): Fragment = CommentsFragment()

    override fun configToolbar() {
        toolbar.apply {
            title = "Komentarze"
        }
    }

}