package com.klima7.services.client.features.newjob.service

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentCategoryBinding
import com.klima7.services.client.databinding.FragmentServiceBinding
import com.klima7.services.client.features.newjob.ProgressItem
import com.klima7.services.common.models.Category
import com.klima7.services.common.platform.BaseFragment
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.xwray.groupie.groupiex.plusAssign


class ServiceFragment: BaseFragment<FragmentServiceBinding>() {

    override val layoutId = R.layout.fragment_service
    override val viewModel: ServiceViewModel by viewModel()

    private val groupieAdapter = GroupieAdapter()
    private val categoriesSection = Section()

    override fun init() {
        super.init()

        binding.categoryRecycler.apply {
            adapter = groupieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        groupieAdapter += ProgressItem(1, "Wybierz usługę")
        groupieAdapter += categoriesSection

        viewModel.categories.observe(viewLifecycleOwner, this::updateCategories)
    }

    private fun updateCategories(categories: List<Category>) {
        Log.i("Hello", "Categories received: $categories")

        categoriesSection.clear()
        categories.forEach { category ->
//            categoriesSection += CategoryItem(category, this)
        }
    }
}