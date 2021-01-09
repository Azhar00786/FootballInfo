package com.example.footballinfo.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class HeadToHeadSpinnerAdapter(
    context: Context,
    resourceId: Int,
    private val teamNameList: MutableList<String>
) : ArrayAdapter<String>(context, resourceId, teamNameList) {
    override fun getCount(): Int {
        return teamNameList.size
    }

    override fun getItem(position: Int): String? {
        return teamNameList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return (super.getDropDownView(position, convertView, parent) as TextView).apply {
            text = teamNameList[position]
        }
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return (super.getDropDownView(position, convertView, parent) as TextView).apply {
            text = teamNameList[position]
        }
    }
}