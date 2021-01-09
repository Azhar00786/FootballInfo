package com.example.footballinfo.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.footballinfo.R
import com.example.footballinfo.adapter.TeamsDataRecyclerViewAdapter
import com.example.footballinfo.adapter.adapter_utilities.TopSpacingItemDecoration
import com.example.footballinfo.database.DatabaseGetter
import com.example.footballinfo.model.database_entities.TeamsDataObject
import com.example.footballinfo.view.components.NetworkChecker
import com.example.footballinfo.viewmodels.TeamsDataFragmantViewModel
import kotlinx.android.synthetic.main.fragment_teams_data_fragmant.*
import kotlinx.coroutines.runBlocking


class TeamsDataFragmant : Fragment(), TeamsDataRecyclerViewAdapter.OnClickTeamSelector {
    private lateinit var sharedPrefEditorThree: SharedPreferences.Editor
    private lateinit var sharedPrefThree: SharedPreferences

    private lateinit var viewModel: TeamsDataFragmantViewModel
    private lateinit var viewModelFactory: TeamsDataFragmantViewModel.TeamsDataFragmantVMFactory

    private lateinit var teamsDataList: List<TeamsDataObject>
    private lateinit var teamsDataRvAdapter: TeamsDataRecyclerViewAdapter

    private lateinit var sharedPrefLeagueId: SharedPreferences
    private lateinit var sharedPefLeagueIdEditor: SharedPreferences.Editor

    private lateinit var recordCounter: SharedPreferences
    private lateinit var recordCounterEditor: SharedPreferences.Editor

    private lateinit var argsLeagueId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TeamsDataFragmant", "onCreate() running")

        viewModelFactory = TeamsDataFragmantViewModel.TeamsDataFragmantVMFactory(
            DatabaseGetter.getInstance(requireContext())
        )
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(TeamsDataFragmantViewModel::class.java)

        sharedPrefThree = requireActivity().getPreferences(Context.MODE_PRIVATE)
        sharedPrefEditorThree = sharedPrefThree.edit()

        sharedPrefLeagueId = requireActivity().getPreferences(Context.MODE_PRIVATE)
        sharedPefLeagueIdEditor = sharedPrefLeagueId.edit()

        recordCounter = requireActivity().getPreferences(Context.MODE_PRIVATE)
        recordCounterEditor = recordCounter.edit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TeamsDataFragmant", "onCreateView() running: ")
        if (NetworkChecker.isOnline(requireContext())) {

            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_teams_data_fragmant, container, false)
        }
        NetworkChecker.errorHandler(
            R.id.action_teamsDataFragmant_self,
            requireContext(),
            findNavController()
        )
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = runBlocking {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TeamsDataFragmant", "onViewCreated() running: ")
        if (NetworkChecker.isOnline(requireContext())) {
            argsLeagueId = sharedPrefLeagueId.getString("leagueId", "0").toString()

            val flagThree = sharedPrefThree.getInt("flagHolderThree", 0)
            if (flagThree == 0) {
                viewModel.cacheTeamsData(argsLeagueId!!)

                sharedPrefEditorThree.putInt("flagHolderThree", 1)
                sharedPrefEditorThree.commit()
            }

            val recordCounter = recordCounter.getInt("recordCounter", 0)
            if (recordCounter == 0) {
                recordCounterEditor.putInt("recordCounter", 1)
                recordCounterEditor.commit()

                viewModel.cacheStandingsData(argsLeagueId)
            }
            viewModel.teamsDataProcess().observeForever {
                teamsDataList = it
            }

            initRecyclerView()
            submitDataToRv()
        }
    }

    private fun submitDataToRv() {
        teamsDataRvAdapter.teamDataListAcceptor(teamsDataList)
    }

    private fun initRecyclerView() {
        teamsDataRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            teamsDataRvAdapter = TeamsDataRecyclerViewAdapter(this@TeamsDataFragmant)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            adapter = teamsDataRvAdapter
        }
    }

    override fun getTeamId(teamId: Int) {
        viewModel.cacheTeamId(teamId)
        view?.findNavController()?.navigate(R.id.action_teamsDataFragmant_to_forHandlingTeamData2)
    }

    override fun onPause() {
        super.onPause()
        viewModel.teamsDataProcess().removeObservers(this)
    }
}
