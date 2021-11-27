package com.klima7.services.client.features.newjob

import android.content.Intent
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.klima7.services.client.R
import com.klima7.services.client.databinding.FragmentCategoryBinding
import com.klima7.services.client.features.jobsetup.JobSetupActivity
import com.klima7.services.common.models.Category
import com.klima7.services.common.platform.BaseFragment
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.groupiex.plusAssign
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewJobFragment: BaseFragment<FragmentCategoryBinding>(), CategoryItem.Listener {

    override val layoutId = R.layout.fragment_category
    override val viewModel: NewJobViewModel by viewModel()

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
        categoriesSection.clear()
        categories.forEach { category ->
            categoriesSection += CategoryItem(category, this)
        }
    }

    override fun onCategoryClicked(category: Category) {
        showServiceScreen(category)
    }

    private fun showServiceScreen(category: Category) {
        val intent = Intent(activity, JobSetupActivity::class.java)
        val bundle = bundleOf(
            "categoryId" to category.id,
            "categoryName" to category.name,
            "exit" to "slideRight",
        )
        intent.putExtras(bundle)
        startActivity(intent)
        Animatoo.animateSlideLeft(requireActivity())
    }
}