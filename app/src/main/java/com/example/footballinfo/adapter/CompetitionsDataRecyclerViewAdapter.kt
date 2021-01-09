package com.example.footballinfo.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.footballinfo.R
import com.example.footballinfo.model.database_entities.CompetitionsDataObject
import kotlinx.android.synthetic.main.layout_for_competitionfragment_recyclerview.view.*

class CompetitionsDataRecyclerViewAdapter(recyclerViewClickHandler: RecyclerviewClickHandler) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var competitionDataList: List<CompetitionsDataObject> = emptyList()
    private var recyclerViewClickHandle: RecyclerviewClickHandler

    init {
        recyclerViewClickHandle = recyclerViewClickHandler
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CompetitionsDataViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.layout_for_competitionfragment_recyclerview, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return competitionDataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CompetitionsDataViewHolder -> {
                holder.bind(competitionDataList.get(position), recyclerViewClickHandle)
            }
        }
    }

    fun submitCompetitionDataList(competitionData: List<CompetitionsDataObject>) {
        competitionDataList = competitionData
    }

    class CompetitionsDataViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        lateinit var leagueId: String
        private lateinit var recyclerViewHandle: RecyclerviewClickHandler
        val countryImage = itemView.imageView2
        val leagueImage = itemView.imageView3
        val leagueSeason = itemView.leagueSeason
        val leagueName = itemView.leagueName

        fun bind(
            competitionsdataobject: CompetitionsDataObject,
            recyclerViewClickHandler: RecyclerviewClickHandler
        ) {
            val requestOptions =
                RequestOptions().placeholder(R.drawable.ic_baseline_broken_image_24)
                    .error(R.drawable.ic_baseline_broken_image_24)

            Glide.with(itemView.context).applyDefaultRequestOptions(requestOptions)
                .load(competitionsdataobject.countryLogo).into(countryImage)
            Glide.with(itemView.context).applyDefaultRequestOptions(requestOptions)
                .load(competitionsdataobject.leagueLogo).into(leagueImage)

            leagueSeason.setText(competitionsdataobject.leagueSeason)
            leagueName.setText(competitionsdataobject.leagueName)
            leagueId = competitionsdataobject.leagueId
            recyclerViewHandle = recyclerViewClickHandler

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            v!!.setBackgroundColor(Color.LTGRAY)
            recyclerViewHandle.recyclerViewClick(leagueId)
        }
    }

    interface RecyclerviewClickHandler {
        fun recyclerViewClick(leagueId: String)
    }

}