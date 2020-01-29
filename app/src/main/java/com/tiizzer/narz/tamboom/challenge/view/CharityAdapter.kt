package com.tiizzer.narz.tamboom.challenge.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.tiizzer.narz.tamboom.challenge.R
import com.tiizzer.narz.tamboom.challenge.model.CharityViewData
import kotlinx.android.synthetic.main.charity_item_layout.view.*

class CharityAdapter: BaseAdapter() {
    private val charities = ArrayList<CharityViewData>()

    override fun getItem(position: Int): CharityViewData = this.charities[position]
    override fun getItemId(position: Int): Long = this.charities[position].id.toLong()
    override fun getCount(): Int = this.charities.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var viewHolder: ItemViewHolder? = null
        var view = convertView
        if(view == null){
            view = LayoutInflater.from(parent.context).inflate(R.layout.charity_item_layout, parent, false)
            viewHolder = ItemViewHolder(view)
        } else {
            viewHolder = view.tag as ItemViewHolder?
        }

        val item = getItem(position)
        viewHolder?.title?.text = item.name

        Glide
            .with(parent.context)
            .load(item.imageURL)
            .into(viewHolder?.image!!)

        return view!!
    }

    fun addItems(items: List<CharityViewData>){
        this.charities.addAll(items)
    }

    fun clear() {
        this.charities.clear()
    }

    private inner class ItemViewHolder(val view: View){
        val image = this.view.image
        val title = this.view.title
    }
}