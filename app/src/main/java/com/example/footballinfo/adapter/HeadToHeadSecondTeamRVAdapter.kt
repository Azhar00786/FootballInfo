package com.example.footballinfo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.footballinfo.R
import com.example.footballinfo.model.database_entities.SecondTeamResultsDataObject
import kotlinx.android.synthetic.main.rowstructure_fragment_htoh_firstteam.view.*

class HeadToHeadSecondTeamRVAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var teamData: List<SecondTeamResultsDataObject>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SecondTeamDataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rowstructure_fragment_htoh_firstteam, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SecondTeamDataViewHolder -> {
                holder.bind(teamData[position])
            }
        }
    }

    fun setDataForSecondTeamRV(secondTeamResultsDataObjects: List<SecondTeamResultsDataObject>) {
        teamData = secondTeamResultsDataObjects
    }

    override fun getItemCount(): Int {
        return teamData.size
    }

    class SecondTeamDataViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val firstTeamName: TextView = itemView.firstTeamName
        private val firstTeamScore: TextView = itemView.firstTeamScore
        private val secondTeamScore: TextView = itemView.secondTeamScore
        private val secondTeamName: TextView = itemView.secondTeamName
        private val leagueName: TextView = itemView.rowLeagueName
        private val matchDate: TextView = itemView.matchDate

        fun bind(secondTeamResultsDataObject: SecondTeamResultsDataObject) {
            firstTeamName.text = secondTeamResultsDataObject.matchHometeamName
            firstTeamScore.text = secondTeamResultsDataObject.matchHometeamScore
            secondTeamName.text = secondTeamResultsDataObject.matchAwayteamName
            secondTeamScore.text = secondTeamResultsDataObject.matchAwayteamScore
            leagueName.text = secondTeamResultsDataObject.leagueName
            matchDate.text = secondTeamResultsDataObject.matchDate
        }
    }

}