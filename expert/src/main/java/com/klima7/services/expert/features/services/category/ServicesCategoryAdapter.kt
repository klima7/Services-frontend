package com.klima7.services.expert.features.services.category

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.content.Context
import android.view.LayoutInflater
import android.widget.CheckBox
import com.klima7.services.expert.R


class ServicesCategoryAdapter(context: Context, private var selectableServices: List<SelectableService> = listOf()) :
    BaseAdapter() {

    private var inflater: LayoutInflater? = null

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount() = selectableServices.size

    override fun getItem(position: Int) = selectableServices[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var vi = convertView
        if (vi == null) vi = inflater?.inflate(R.layout.entry_services_list, null)
        val selectableService = selectableServices[position]
        val checkbox = vi?.findViewById<CheckBox>(R.id.services_entry_checkbox)
        checkbox?.text = selectableService.service.name
        checkbox?.isChecked = selectableService.selected
        checkbox?.setOnClickListener {
            selectableService.selected = checkbox.isChecked
        }
        return vi!!
    }

    fun setServices(selectableServices: List<SelectableService>) {
        this.selectableServices = selectableServices
        super.notifyDataSetChanged()
        super.notifyDataSetInvalidated()
    }
}