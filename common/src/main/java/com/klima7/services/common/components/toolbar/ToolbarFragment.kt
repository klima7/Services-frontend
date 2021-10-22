package com.klima7.services.common.components.toolbar

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.klima7.services.common.R
import com.klima7.services.common.databinding.FragmentToolbarBinding
import com.klima7.services.common.platform.BaseFragment
import com.klima7.services.common.platform.BaseViewModel

abstract class ToolbarFragment(
    private val menu: Int? = null,
    private val back: Boolean = false
): BaseFragment<FragmentToolbarBinding>() {

    override val layoutId = R.layout.fragment_toolbar
    override val viewModel = BaseViewModel()

    // Toolbar for easy access
    protected lateinit var toolbar: Toolbar

    // Values to override
    abstract fun fragment(): Fragment
    open fun configToolbar() {}
    open fun itemClicked(item: MenuItem) {}


    override fun init() {
        super.init()
        toolbar = binding.toolbar
        configMenu()
        configBackArrow()
        configToolbar()
    }

    override fun onFirstCreation() {
        super.onFirstCreation()
        val frag = fragment()
        frag.arguments = arguments

        childFragmentManager
            .beginTransaction()
            .add(R.id.toolbar_container_view, frag)
            .commit()
    }

    private fun configMenu() {
        val cMenu = menu
        if(cMenu != null) {
            toolbar.inflateMenu(cMenu)
        }

        toolbar.setOnMenuItemClickListener { item ->
            if(item != null) {
                itemClicked(item)
            }
            true
        }
    }

    private fun configBackArrow() {
        if(back) {
            toolbar.setNavigationIcon(R.drawable.icon_arrow_back)
            toolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        }
    }

}
