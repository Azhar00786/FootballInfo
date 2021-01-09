package com.example.footballinfo.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.footballinfo.R
import com.example.footballinfo.model.database_entities.StandingsDataObject

class TeamStandingRecyclerViewAdapter(
    private val teamStandingData: List<StandingsDataObject>,
    private val teamId: Int,
    private val mainContext: Context
) : RecyclerView.Adapter<TeamStandingRecyclerViewAdapter.TeamStandingVH>() {
    class TeamStandingVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var teamStandingNumber: TextView = itemView.findViewById(R.id.teamStandingsNumberData)
        var teamName: TextView = itemView.findViewById(R.id.teamNameData)
        var teamSymbol: ImageView = itemView.findViewById(R.id.teamSymbolImage)
        var leaguePoints: TextView = itemView.findViewById(R.id.leaguePointsData)
        var matchesPlayed: TextView = itemView.findViewById(R.id.matchesPlayedData)
        var leagueGA: TextView = itemView.findViewById(R.id.leagueGAData)
        var leagueGF: TextView = itemView.findViewById(R.id.leagueGFData)
        var leagueWins: TextView = itemView.findViewById(R.id.leagueWinsData)
        var leagueLoss: TextView = itemView.findViewById(R.id.leagueLossData)
        var leagueDraw: TextView = itemView.findViewById(R.id.leagueDrawData)

        var parentLayout: RelativeLayout = itemView.findViewById(R.id.teamStandingsDataLinearLayout)
        var expandableDataLayout: RelativeLayout =
            itemView.findViewById(R.id.expandableTeamStandingData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamStandingVH {
        val view: View = LayoutInflater.from(mainContext)
            .inflate(R.layout.rowstructure_fragment_teamstanding, parent, false)
        return TeamStandingVH(view)
    }

    override fun onBindViewHolder(holder: TeamStandingVH, position: Int) {
        val teamStandingDataSingleObj: StandingsDataObject = teamStandingData[position]

        holder.teamStandingNumber.text = teamStandingDataSingleObj.overallLeaguePosition
        holder.teamName.text = teamStandingDataSingleObj.teamName

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)

        Glide.with(mainContext)
            .applyDefaultRequestOptions(requestOptions)
            .load(teamStandingDataSingleObj.teamBadge)
            .into(holder.teamSymbol)

        holder.leaguePoints.text = teamStandingDataSingleObj.overallLeaguePTS
        holder.matchesPlayed.text = teamStandingDataSingleObj.overallLeaguePayed
        holder.leagueGA.text = teamStandingDataSingleObj.overallLeagueGA
        holder.leagueGF.text = teamStandingDataSingleObj.overallLeagueGF
        holder.leagueWins.text = teamStandingDataSingleObj.overallLeagueW
        holder.leagueLoss.text = teamStandingDataSingleObj.overallLeagueL
        holder.leagueDraw.text = teamStandingDataSingleObj.overallLeagueD

        val isExpandable: Boolean = teamStandingData[position].expandable
        holder.expandableDataLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE
        holder.parentLayout.setOnClickListener {
            val teamStandingData = teamStandingData[position]
            teamStandingData.expandable = !teamStandingData.expandable
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return teamStandingData.size
    }
}