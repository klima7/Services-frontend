package com.klima7.services.common.components.profile.comments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.klima7.services.common.R
import com.klima7.services.common.components.views.RatingView
import com.klima7.services.common.models.Rating


class ProfileCommentsLatestAdapter(context: Context, private var ratings: List<Rating> = listOf()) :
    BaseAdapter() {

    private var inflater: LayoutInflater? = null

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount() = ratings.size

    override fun getItem(position: Int) = ratings[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var vi = convertView
        if (vi == null) vi = inflater?.inflate(R.layout.element_comment, null)
        val rwpi = ratings[position]
        val ratingView = vi?.findViewById<RatingView>(R.id.comment_rating_view)
        ratingView?.setRating(rwpi)
        return vi!!
    }

    fun setRatings(ratings: List<Rating>) {
        this.ratings = ratings
        super.notifyDataSetChanged()
        super.notifyDataSetInvalidated()
    }
}