package com.example.footballinfo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.footballinfo.R
import com.example.footballinfo.model.database_entities.PlayersItemDataObject

class SingleTeamDataRecyclerViewAdapter(val playersList: List<PlayersItemDataObject>) :
    RecyclerView.Adapter<SingleTeamDataRecyclerViewAdapter.RecyclerViewVH>() {

    class RecyclerViewVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var playerName: TextView = itemView.findViewById(R.id.playerNameData)
        var age: TextView = itemView.findViewById(R.id.playerAgeData)
        var country: TextView = itemView.findViewById(R.id.playerCountryData)
        var jerseyNumber: TextView = itemView.findViewById(R.id.playerJerseyNumberData)
        var position: TextView = itemView.findViewById(R.id.playerPositionData)
        var matchesPlayed: TextView = itemView.findViewById(R.id.playerMatchesData)
        var goals: TextView = itemView.findViewById(R.id.playerGoalsData)
        var yellowCards: TextView = itemView.findViewById(R.id.playerYellowCardsData)
        var redCards: TextView = itemView.findViewById(R.id.playerRedCardsData)

        var linearLayout: LinearLayout = itemView.findViewById(R.id.playerLinearLayout)
        var expandableRelativeLayout: RelativeLayout =
            itemView.findViewById(R.id.expandableDataLayout)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewVH {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.rowstructure_fragment_single_team_detail, parent, false)
        return RecyclerViewVH(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewVH, position: Int) {
        val playerDataSingleObj: PlayersItemDataObject = playersList[position]
        holder.playerName.text = playerDataSingleObj.playerName
        holder.age.text = playerDataSingleObj.playerAge
        holder.country.text = playerDataSingleObj.playerCountry
        holder.jerseyNumber.text = playerDataSingleObj.playerNumber
        holder.position.text = playerDataSingleObj.playerType
        holder.matchesPlayed.text = playerDataSingleObj.playerMatchPlayed
        holder.goals.text = playerDataSingleObj.playerGoals
        holder.yellowCards.text = playerDataSingleObj.playerYellowCards
        holder.redCards.text = playerDataSingleObj.playerRedCards

        val isExpandable: Boolean = playersList[position].isExpandable
        holder.expandableRelativeLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.linearLayout.setOnClickListener {
            val playerLists = playersList[position]
            playerLists.isExpandable = !playerLists.isExpandable
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return playersList.size
    }
}