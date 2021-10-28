package com.klima7.services.common.platform

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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

    abstract fun fragment(): Fragment

}