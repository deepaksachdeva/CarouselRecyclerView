package com.deepak.carouselrecyclerview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class DateAdapter(private val dateDataList: ArrayList<LabelerDate>, private val paddingWidthDate: Int)
    : RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    private var selectionItem = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        if(viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_date, parent, false)
            return DateViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_date, parent, false)
            val layoutParams =  view.layoutParams as RecyclerView.LayoutParams
            layoutParams.width = paddingWidthDate
            view.layoutParams = layoutParams
            return DateViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val labelerDate: LabelerDate = dateDataList[position]
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            holder.tvDate.text = labelerDate.number
            holder.tvDate.visibility = View.VISIBLE

            if(position == selectionItem) {
                holder.tvDate.setBackgroundResource(R.drawable.rounded_blue_view)
                holder.tvDate.setTextColor(Color.WHITE)
                holder.tvDate.textSize = 35f
            } else {
                holder.tvDate.setBackgroundResource(R.drawable.rounded_gray_view)
                holder.tvDate.setTextColor(Color.BLACK)
                holder.tvDate.textSize = 18f
            }
        } else {
            holder.tvDate.visibility = View.INVISIBLE
        }
    }


    override fun getItemCount(): Int {
        return dateDataList.size
    }

    override fun getItemViewType(position: Int): Int {
        val labelerDate: LabelerDate = dateDataList[position]
        if(labelerDate.type == VIEW_TYPE_PADDING) {
            return VIEW_TYPE_PADDING
        } else {
            return VIEW_TYPE_ITEM
        }
    }

    fun setSelectionItem(item: Int) {
        this.selectionItem = item
        notifyDataSetChanged()
    }

    inner class DateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvDate: TextView
//        var tvNotification: TextView
        init {
            tvDate = itemView.findViewById(R.id.txtDate) as TextView
//            tvNotification = itemView.findViewById(R.id.tvNotification) as TextView
        }
    }

    companion object {
        val VIEW_TYPE_PADDING = 1
        val VIEW_TYPE_ITEM = 2
        private val TAG = DateAdapter::class.java.simpleName
    }
}