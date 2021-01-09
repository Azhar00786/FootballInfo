package com.example.footballinfo.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.footballinfo.R
import com.example.footballinfo.adapter.TeamStandingRecyclerViewAdapter
import com.example.footballinfo.database.DatabaseGetter
import com.example.footballinfo.model.database_entities.StandingsDataObject
import com.example.footballinfo.viewmodels.TeamStandingsFragmentViewModel
import kotlinx.android.synthetic.main.fragment_teams_standings.*

class TeamStandingsFragment : Fragment() {

    private lateinit var viewModel: TeamStandingsFragmentViewModel
    private lateinit var viewModelFactory: TeamStandingsFragmentViewModel.TeamStandingsFragmentVMFactory
    private lateinit var teamStandingData: List<StandingsDataObject>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = TeamStandingsFragmentViewModel.TeamStandingsFragmentVMFactory(
            DatabaseGetter.getInstance(requireContext())
        )
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(TeamStandingsFragmentViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        teamDetailText.text =
            "${viewModel.getSelectedTeamName()} is at ${viewModel.getSelectedTeamPosition()} position in league table. Tap team name to get that team league statistics."
        initData()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val recyclerViewAdapter = TeamStandingRecyclerViewAdapter(
            teamStandingData,
            viewModel.getStoredTeamKey(),
            requireContext()
        )
        teamStandingRecyclerView.adapter = recyclerViewAdapter
        teamStandingRecyclerView.setHasFixedSize(true)
    }

    private fun initData() {
        viewModel.getTeamStandingsData().observeForever {
            teamStandingData = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teams_standings, container, false)
    }

    override fun onPause() {
        super.onPause()
        viewModel.getTeamStandingsData().removeObservers(this)
    }
}