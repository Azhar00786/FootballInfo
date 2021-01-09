package com.example.footballinfo.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.footballinfo.R
import com.example.footballinfo.adapter.SingleTeamDataRecyclerViewAdapter
import com.example.footballinfo.database.DatabaseGetter
import com.example.footballinfo.model.database_entities.PlayersItemDataObject
import com.example.footballinfo.viewmodels.SingleTeamDetailFragmentViewModel
import kotlinx.android.synthetic.main.fragment_single_team_detail.*

class SingleTeamDetailFragment : Fragment() {

    private lateinit var viewModel: SingleTeamDetailFragmentViewModel
    private lateinit var viewModelFactory: SingleTeamDetailFragmentViewModel.SingleTeamDetailFragmentVMFactory


    lateinit var singleTeamData: List<PlayersItemDataObject>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelFactory = SingleTeamDetailFragmentViewModel.SingleTeamDetailFragmentVMFactory(
            DatabaseGetter.getInstance(requireContext())
        )
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(SingleTeamDetailFragmentViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamName.text = viewModel.getSelectedTeamName()

        viewModel.getCoachData().observeForever {
            coachNameData.text = it.coachName
            coachAgeData.text = it.coachAge
            coachCountryData.text = it.coachCountry
        }

        initData()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val adapter = SingleTeamDataRecyclerViewAdapter(singleTeamData)
        teamPlayersRecyclerView.adapter = adapter
        teamPlayersRecyclerView.setHasFixedSize(true)
    }

    private fun initData() {
        viewModel.getSingleTeamData().observeForever {
            singleTeamData = it
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_team_detail, container, false)
    }

    override fun onPause() {
        super.onPause()
        viewModel.getCoachData().removeObservers(this)
        viewModel.getSingleTeamData().removeObservers(this)
    }
}