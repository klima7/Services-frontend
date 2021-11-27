package com.klima7.services.client.features.profile

import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.klima7.services.common.platform.BaseActivity

class ProfileActivity: BaseActivity() {
    private val fragment = ProfileFragment()

    override fun fragment() = fragment

    override fun onBackPressed() {
        val exit = intent.getStringExtra("exit")
        finish()
        if(exit == "slideDown") {
            Animatoo.animateSlideDown(this)
        }
    }
}