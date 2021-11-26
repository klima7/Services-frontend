package com.klima7.services.client.features.jobsetup

import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.klima7.services.common.platform.BaseActivity

class JobSetupActivity: BaseActivity() {

    private val newJobFragment = JobSetupFragment()

    override fun fragment() = newJobFragment

    override fun onBackPressed() {
        newJobFragment.backButtonClicked()
    }

    override fun overrideStartTransition() {
        super.overrideStartTransition()
        Animatoo.animateSlideLeft(this)
    }

    override fun overrideExitTransition() {
        super.overrideExitTransition()
        Animatoo.animateSlideRight(this)
    }

}
