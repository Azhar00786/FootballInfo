package com.example.footballinfo.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.footballinfo.R
import com.example.footballinfo.adapter.HeadToHeadFirstTeamRVAdapter
import com.example.footballinfo.adapter.HeadToHeadSecondTeamRVAdapter
import com.example.footballinfo.adapter.HeadToHeadSpinnerAdapter
import com.example.footballinfo.database.DatabaseGetter
import com.example.footballinfo.view.components.NetworkChecker
import com.example.footballinfo.viewmodels.HeadToHeadTeamDataFragmentVMFactory
import com.example.footballinfo.viewmodels.HeadToHeadTeamDataFragmentViewModel
import kotlinx.android.synthetic.main.fragment_headtohead_data.*
import kotlinx.coroutines.runBlocking

class HeadToHeadTeamDataFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var viewModel: HeadToHeadTeamDataFragmentViewModel
    private lateinit var viewModelFactory: HeadToHeadTeamDataFragmentVMFactory
    private var flagTwo: Int = 0

    private lateinit var allTeamData: MutableList<String>
    private lateinit var selectedTeamName: String
    private lateinit var teamNameFromSpinner: String

    private lateinit var firstTeamRVAdapter: HeadToHeadFirstTeamRVAdapter
    private lateinit var secondTeamRVAdapter: HeadToHeadSecondTeamRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelFactory =
            HeadToHeadTeamDataFragmentVMFactory(DatabaseGetter.getInstance(requireContext()))
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(HeadToHeadTeamDataFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (NetworkChecker.isOnline(requireContext())) {
            return inflater.inflate(R.layout.fragment_headtohead_data, container, false)
        }
        NetworkChecker.errorHandler(
            R.id.action_headToHeadTeamDataFragment_self,
            requireContext(),
            findNavController()
        )
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedTeamName = viewModel.getSelectedTeamName()
        teamNameHtoH.text = selectedTeamName
        allTeamData = viewModel.allTeamsName()
        allTeamData.remove(selectedTeamName)

        headToHeadSpinner.adapter = HeadToHeadSpinnerAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            allTeamData
        )
        firstTeamRVAdapter = HeadToHeadFirstTeamRVAdapter()
        secondTeamRVAdapter = HeadToHeadSecondTeamRVAdapter()
        headToHeadSpinner.onItemSelectedListener = this@HeadToHeadTeamDataFragment
        headToHeadSubmitButton.setOnClickListener {
            if (NetworkChecker.isOnline(requireContext())) {
                if (viewModel.getCountOfFirstTeamTableRecords() == 0 && viewModel.getCountOfSecondTeamTableRecords() == 0) {
                    if (flagTwo == 0) {
                        firstTeamNameDisplay.text = selectedTeamName
                        secondTeamNameDisplay.text = teamNameFromSpinner
                        cacheData(selectedTeamName, teamNameFromSpinner)
                        firstTeamDataRV.adapter = firstTeamRVAdapter
                        secondTeamDataRV.adapter = secondTeamRVAdapter
                        viewModel.getFirstTeamData().observeForever {
                            firstTeamRVAdapter.setDataForFirstTeamRV(it)
                        }
                        viewModel.getSecondTeamData().observeForever {
                            secondTeamRVAdapter.setDataForSecondTeamRV(it)
                        }
                        flagTwo = 1
                    }
                } else {
                    if (flagTwo == 0) {
                        deleteData()
                        firstTeamNameDisplay.text = selectedTeamName
                        secondTeamNameDisplay.text = teamNameFromSpinner
                        cacheData(selectedTeamName, teamNameFromSpinner)

                        firstTeamDataRV.adapter = firstTeamRVAdapter
                        secondTeamDataRV.adapter = secondTeamRVAdapter
                        viewModel.getFirstTeamData().observeForever {
                            firstTeamRVAdapter.setDataForFirstTeamRV(it)
                        }
                        viewModel.getSecondTeamData().observeForever {
                            secondTeamRVAdapter.setDataForSecondTeamRV(it)
                        }

                        flagTwo = 1
                    }
                }
            } else {
                NetworkChecker.errorHandler(
                    R.id.action_headToHeadTeamDataFragment_self,
                    requireContext(),
                    findNavController()
                )
            }
        }
    }


    private fun cacheData(teamOneName: String, teamTwoName: String) = runBlocking {
        viewModel.cacheHeadToHeadData(teamOneName, teamTwoName)
    }

    private fun deleteData() = runBlocking {
        viewModel.deleteHeadToHeadData()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        teamNameFromSpinner = parent!!.getItemAtPosition(position) as String
        flagTwo = 0
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}


    override fun onPause() {
        super.onPause()
        viewModel.getFirstTeamData().removeObservers(this)
        viewModel.getSecondTeamData().removeObservers(this)
    }
}