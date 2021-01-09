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
import com.example.footballinfo.adapter.CompetitionsDataRecyclerViewAdapter
import com.example.footballinfo.database.DatabaseGetter
import com.example.footballinfo.model.database_entities.CompetitionsDataObject
import com.example.footballinfo.view.components.NetworkChecker
import com.example.footballinfo.viewmodels.CompetitionsDataFragmantVMFactory
import com.example.footballinfo.viewmodels.CompetitionsDataFragmantViewModel
import kotlinx.android.synthetic.main.fragment_competitions_data_fragmant.*
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates

class CompetitionsDataFragmant : Fragment(),
    CompetitionsDataRecyclerViewAdapter.RecyclerviewClickHandler {
    private lateinit var viewModel: CompetitionsDataFragmantViewModel
    private lateinit var viewModelFactory: CompetitionsDataFragmantVMFactory

    private lateinit var competitionsDataList: List<CompetitionsDataObject>

    private lateinit var sharedPrefTwo: SharedPreferences
    private lateinit var sharedPrefEditiorTwo: SharedPreferences.Editor

    private lateinit var sharedPrefThree: SharedPreferences
    private lateinit var sharedPrefEditorThree: SharedPreferences.Editor

    private lateinit var sharedPreferncesCountryId: SharedPreferences
    private lateinit var sharedPreferencesEditorCountryId: SharedPreferences.Editor

    private lateinit var sharedPrefLeagueId: SharedPreferences
    private lateinit var sharedPrefLeagueIdEditor: SharedPreferences.Editor

    private lateinit var recordCounter: SharedPreferences
    private lateinit var recordCounterEditor: SharedPreferences.Editor

    private lateinit var competitionsDataRvAdapter: CompetitionsDataRecyclerViewAdapter
    private var argsCountryId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CompetitionsDataFrag", "onCreate running")

        sharedPrefTwo = requireActivity().getPreferences(Context.MODE_PRIVATE)
        sharedPrefEditiorTwo = sharedPrefTwo.edit()

        //For TeamDataFragmant
        sharedPrefThree = requireActivity().getPreferences(Context.MODE_PRIVATE)
        sharedPrefEditorThree = sharedPrefThree.edit()

        //for using countryId coming from CountriesDisplayFragment
        sharedPreferncesCountryId = requireActivity().getPreferences(Context.MODE_PRIVATE)
        sharedPreferencesEditorCountryId = sharedPreferncesCountryId.edit()

        //For sending leagueId value to teamsDataFragment
        sharedPrefLeagueId = requireActivity().getPreferences(Context.MODE_PRIVATE)
        sharedPrefLeagueIdEditor = sharedPrefLeagueId.edit()

        recordCounter = requireActivity().getPreferences(Context.MODE_PRIVATE)
        recordCounterEditor = recordCounter.edit()

        viewModelFactory =
            CompetitionsDataFragmantVMFactory(DatabaseGetter.getInstance(requireContext()))
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(CompetitionsDataFragmantViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("CompetitionsDataFrag", "onCreateView running")
        if (NetworkChecker.isOnline(requireContext())) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_competitions_data_fragmant, container, false)
        }
        NetworkChecker.errorHandler(
            R.id.action_competitionsDataFragmant_self,
            requireContext(),
            findNavController()
        )
        return null

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = runBlocking {
        super.onViewCreated(view, savedInstanceState)
        if (NetworkChecker.isOnline(requireContext())) {
            argsCountryId = sharedPreferncesCountryId.getInt("countryId", 0)

            val flagThree = sharedPrefThree.getInt("flagHolderThree", 0)
            if (flagThree == 1) {
                viewModel.deleteTeamsCachedData()
                sharedPrefEditorThree.putInt("flagHolderThree", 0)
                sharedPrefEditorThree.commit()
            }

            val recordCounter = recordCounter.getInt("recordCounter", 0)
            if (recordCounter == 1) {
                viewModel.deleteCacheTeamStandingData()
                recordCounterEditor.putInt("recordCounter", 0)
                recordCounterEditor.commit()
            }

            val flagTwo = sharedPrefTwo.getInt("flagHolderTwo", 0)
            if (flagTwo == 0) {
                viewModel.cacheCompetitionsData(argsCountryId.toString())
                sharedPrefEditiorTwo.putInt("flagHolderTwo", 1)
                sharedPrefEditiorTwo.commit()
            }

            viewModel.competitionsDataProcess().observeForever {
                competitionsDataList = it
            }

            initRecyclerView()
            listSubmiterForRv()
        }
    }

    private fun listSubmiterForRv() {
        competitionsDataRvAdapter.submitCompetitionDataList(competitionsDataList)
    }

    private fun initRecyclerView() {
        competitionsDataRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            competitionsDataRvAdapter =
                CompetitionsDataRecyclerViewAdapter(this@CompetitionsDataFragmant)
            //addItemDecoration(DividerItemDecoration(requireContext(),LinearLayout.HORIZONTAL))
            adapter = competitionsDataRvAdapter
        }
    }

    override fun recyclerViewClick(leagueId: String) {
        sharedPrefLeagueIdEditor.putString("leagueId", leagueId)
        sharedPrefLeagueIdEditor.commit()

        view?.findNavController()
            ?.navigate(R.id.action_competitionsDataFragmant_to_teamsDataFragmant)
    }

    override fun onPause() {
        super.onPause()
        viewModel.competitionsDataProcess().removeObservers(this)
    }
}