package com.klima7.services.common.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.klima7.services.common.R

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)
        addFragment(savedInstanceState)
    }

    private fun addFragment(savedInstanceState: Bundle?) {
        savedInstanceState ?:
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, fragment().apply { arguments=intent.extras })
                .commit()
    }

    override fun finish() {
        val exit = intent.getStringExtra("exit")
        super.finish()
        when(exit) {
            "slideDown" -> Animatoo.animateSlideDown(this)
            "slideRight" -> Animatoo.animateSlideRight(this)
        }
    }

    abstract fun fragment(): Fragment
}