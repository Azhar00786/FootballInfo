package com.example.footballinfo.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.footballinfo.R
import com.example.footballinfo.model.database_entities.TeamsDataObject
import kotlinx.android.synthetic.main.layout_for_teamsdatafragment_recyclerview.view.*
import kotlin.properties.Delegates

class TeamsDataRecyclerViewAdapter(onClickTeamSelectorForAll: OnClickTeamSelector) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var teamDataList: List<TeamsDataObject>
    private var onClickTeamSelector: OnClickTeamSelector

    init {
        onClickTeamSelector = onClickTeamSelectorForAll
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TeamDataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_for_teamsdatafragment_recyclerview, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TeamDataViewHolder -> {
                holder.bind(teamDataList.get(position), onClickTeamSelector)
            }
        }
    }

    override fun getItemCount(): Int {
        return teamDataList.size
    }

    fun teamDataListAcceptor(teamsDataList: List<TeamsDataObject>) {
        teamDataList = teamsDataList
    }

    class TeamDataViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val teamLogoImage = itemView.teamLogo
        val teamName = itemView.teamName
        var teamId by Delegates.notNull<Int>()
        private lateinit var onClickTeamSelector: OnClickTeamSelector

        fun bind(
            teamsDataObject: TeamsDataObject,
            onClickTeamSelectorForMethod: OnClickTeamSelector
        ) {
            teamName.setText(teamsDataObject.teamName)
            val requestOptions =
                RequestOptions().placeholder(R.drawable.ic_baseline_broken_image_24).error(
                    R.drawable.ic_baseline_broken_image_24
                )

            Glide.with(itemView.context).applyDefaultRequestOptions(requestOptions)
                .load(teamsDataObject.teamBadge).into(teamLogoImage)

            teamId = teamsDataObject.teamKey
            onClickTeamSelector = onClickTeamSelectorForMethod
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onClickTeamSelector.getTeamId(teamId)
        }
    }

    interface OnClickTeamSelector {
        fun getTeamId(teamId: Int)
    }
}