package com.example.footballinfo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.footballinfo.R
import com.example.footballinfo.model.database_entities.FirstTeamResultsDataObject
import kotlinx.android.synthetic.main.rowstructure_fragment_htoh_firstteam.view.*

class HeadToHeadFirstTeamRVAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var teamData: List<FirstTeamResultsDataObject>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FirstTeamDataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rowstructure_fragment_htoh_firstteam, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FirstTeamDataViewHolder -> {
                holder.bind(teamData[position])
            }
        }
    }

    fun setDataForFirstTeamRV(firstTeamResultsDataObjects: List<FirstTeamResultsDataObject>) {
        teamData = firstTeamResultsDataObjects
    }

    override fun getItemCount(): Int {
        return teamData.size
    }

    class FirstTeamDataViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val firstTeamName: TextView = itemView.firstTeamName
        private val firstTeamScore: TextView = itemView.firstTeamScore
        private val secondTeamScore: TextView = itemView.secondTeamScore
        private val secondTeamName: TextView = itemView.secondTeamName
        private val leagueName: TextView = itemView.rowLeagueName
        private val matchDate: TextView = itemView.matchDate

        fun bind(firstTeamResultsDataObject: FirstTeamResultsDataObject) {
            firstTeamName.text = firstTeamResultsDataObject.matchHometeamName
            firstTeamScore.text = firstTeamResultsDataObject.matchHometeamScore
            secondTeamName.text = firstTeamResultsDataObject.matchAwayteamName
            secondTeamScore.text = firstTeamResultsDataObject.matchAwayteamScore
            leagueName.text = firstTeamResultsDataObject.leagueName
            matchDate.text = firstTeamResultsDataObject.matchDate
        }

    }
}