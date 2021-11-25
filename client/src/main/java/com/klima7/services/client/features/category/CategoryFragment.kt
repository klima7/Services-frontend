package com.klima7.services.client.features.category

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentCategoryBinding
import com.klima7.services.client.features.newjob.newjob.NewJobActivity
import com.klima7.services.client.features.newjob.service.ServiceActivity
import com.klima7.services.common.models.Category
import com.klima7.services.common.platform.BaseFragment
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.groupiex.plusAssign
import org.koin.androidx.viewmodel.ext.android.viewModel


class CategoryFragment: BaseFragment<FragmentCategoryBinding>(), CategoryItem.Listener {

    override val layoutId = R.layout.fragment_category
    override val viewModel: CategoryViewModel by viewModel()

    private val groupieAdapter = GroupieAdapter()
    private val categoriesSection = Section()

    override fun init() {
        super.init()

        binding.categoryRecycler.apply {
            adapter = groupieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        groupieAdapter += categoriesSection

        viewModel.categories.observe(viewLifecycleOwner, this::updateCategories)
    }

    private fun updateCategories(categories: List<Category>) {
        Log.i("Hello", "Categories received: $categories")

        categoriesSection.clear()
        categories.forEach { category ->
            categoriesSection += CategoryItem(category, this)
        }
    }

    override fun onCategoryClicked(category: Category) {
        showServiceScreen(category)
    }

    private fun showServiceScreen(category: Category) {
//        val intent = Intent(activity, ServiceActivity::class.java)
//        intent.putExtra("categoryId", category.id)
//        intent.putExtra("categoryName", category.name)
//        startActivity(intent)

        val intent = Intent(activity, NewJobActivity::class.java)
        startActivity(intent)
    }
}