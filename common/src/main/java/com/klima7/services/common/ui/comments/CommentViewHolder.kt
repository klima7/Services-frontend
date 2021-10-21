package com.klima7.services.common.ui.comments

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.klima7.services.common.R

class CommentViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val id: TextView = view.findViewById(R.id.comment_id)

}