package com.example.footballinfo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.footballinfo.R
import com.example.footballinfo.model.database_entities.CountryDataObject

class CountryCoverFlowAdapter(
    private val countryData: List<CountryDataObject>,
    private val mainContext: Context
) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var rowView = convertView
        if (convertView == null) {
            rowView = LayoutInflater.from(mainContext).inflate(R.layout.layout_country_item, null)
            val countryName = rowView.findViewById<TextView>(R.id.countryNameLabel)
            val countryImage = rowView.findViewById<ImageView>(R.id.countryFlagImage)

            //set Data
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(mainContext)
                .applyDefaultRequestOptions(requestOptions)
                .load(countryData.get(position).countryLogo)
                .into(countryImage)

            countryName.setText(countryData.get(position).countryName)
        }
        return rowView
    }

    override fun getItem(position: Int): Any {
        return countryData.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return countryData.size
    }
}