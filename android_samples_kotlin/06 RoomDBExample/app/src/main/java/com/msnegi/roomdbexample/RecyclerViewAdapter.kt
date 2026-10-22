package com.msnegi.roomdbexample

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.msnegi.roomdbexample.roomdb.Item

class RecyclerViewAdapter(var list: List<Item?>?, var callBackInterface: CallBackInterface) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_rv_item, parent, false)
        return View_Holder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as View_Holder).title.text = list!![position]!!.item_id
        holder.description.text = list!![position]!!.descripton
        holder.editBtn.setOnClickListener {
            callBackInterface.editItem(
                list!![position]
            )
        }
        holder.deleteBtn.setOnClickListener { callBackInterface.deleteItem(list!![position]) }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    inner class View_Holder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var description: TextView
        var editBtn: ImageView
        var deleteBtn: ImageView

        init {
            title = view.findViewById<View>(R.id.titletxt) as TextView
            description = view.findViewById<View>(R.id.descriptiontxt) as TextView
            editBtn = view.findViewById<View>(R.id.editBtn) as ImageView
            deleteBtn = view.findViewById<View>(R.id.deleteBtn) as ImageView
        }
    }
}