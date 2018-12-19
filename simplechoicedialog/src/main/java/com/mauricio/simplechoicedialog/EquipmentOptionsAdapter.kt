package com.mauricio.simplechoicedialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item_option.view.*

class EquipmentOptionsAdapter(val data: List<Item>, val context: Context) : BaseAdapter() {

    var listener: ((index: Int) -> Unit)? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_option, parent, false)

        view.txt_item_title.text = data[position].title
        view.txt_item_subtitle.text = data[position].subtitle
        view.btn_radio.isChecked = data[position].isChecked

        listener?.let { function ->
            view.btn_radio.setOnClickListener {
                function(position)
            }

            view.setOnClickListener {
                function(position)
            }
        }

        return view
    }

    override fun getItem(position: Int) = data[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = data.size
}
