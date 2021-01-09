package com.example.footballinfo.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.example.footballinfo.R
import com.example.footballinfo.adapter.CountryCoverFlowAdapter
import com.example.footballinfo.database.DatabaseGetter
import com.example.footballinfo.model.database_entities.CountryDataObject
import com.example.footballinfo.view.components.NetworkChecker
import com.example.footballinfo.viewmodels.CountriesDisplayFragmantVMFactory
import com.example.footballinfo.viewmodels.CountriesDisplayFragmentViewModel
import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow
import kotlinx.android.synthetic.main.fragment_countries_display.*
import kotlinx.coroutines.runBlocking


class CountriesDisplayFragment : Fragment() {
    private lateinit var viewModel: CountriesDisplayFragmentViewModel
    private lateinit var viewModelFactory: CountriesDisplayFragmantVMFactory

    //private lateinit var coverFlow: FeatureCoverFlow
    private lateinit var countryAdapter: CountryCoverFlowAdapter
    private lateinit var countryDataList: List<CountryDataObject>

    lateinit var sharedPref: SharedPreferences
    lateinit var sharedPrefEditior: SharedPreferences.Editor

    lateinit var sharedPrefTwo: SharedPreferences
    lateinit var sharedPrefEditiorTwo: SharedPreferences.Editor

    lateinit var sharedPreferncesCountryId: SharedPreferences
    lateinit var sharePreferencesEditorCountryId: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CountriesDisplayFrag", "onCreate running")

        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        sharedPrefEditior = sharedPref.edit()

        //for CompetitonsDataFragment
        sharedPrefTwo = requireActivity().getPreferences(Context.MODE_PRIVATE)
        sharedPrefEditiorTwo = sharedPrefTwo.edit()

        //for storing countryId
        sharedPreferncesCountryId = requireActivity().getPreferences(Context.MODE_PRIVATE)
        sharePreferencesEditorCountryId = sharedPreferncesCountryId.edit()

        viewModelFactory =
            CountriesDisplayFragmantVMFactory(DatabaseGetter.getInstance(requireContext()))
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(CountriesDisplayFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("CountriesDisplayFrag", "onCreateView running")

        if (NetworkChecker.isOnline(requireContext())) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_countries_display, container, false)
        }
        NetworkChecker.errorHandler(
            R.id.action_countriesDisplayFragment_self,
            requireContext(),
            findNavController()
        )
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = runBlocking {
        super.onViewCreated(view, savedInstanceState)
        Log.d("CountriesDisplayFrag", "CountriesDisplayFragment running")
        if (NetworkChecker.isOnline(requireContext())) {
            val flagTwo = sharedPrefTwo.getInt("flagHolderTwo", 0)
            if (flagTwo == 1) {
                viewModel.deleteCompetitionsFragmentCacheData()
                sharedPrefEditiorTwo.putInt("flagHolderTwo", 0)
                sharedPrefEditiorTwo.commit()
            }

            val flag = sharedPref.getInt("flagHolder", 0)
            if (flag == 0) {
                viewModel.cacheCountryData()
                sharedPrefEditior.putInt("flagHolder", 1)
                sharedPrefEditior.commit()
            }
            viewModel.countryDataProcess().observeForever {
                countryDataList = it
            }

            countryName.setFactory {
                val inflater = LayoutInflater.from(requireContext())
                return@setFactory inflater.inflate(R.layout.layout_country_title, null)
            }
            val inAnim: Animation =
                AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_top)
            val outAnim: Animation =
                AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_bottom)
            countryName.inAnimation = inAnim
            countryName.outAnimation = outAnim

            coverFlowHandler(countryDataList)
        }
    }

    private fun coverFlowHandler(countryDataList: List<CountryDataObject>) {
        Log.d("CountriesDisplayFrag", "coverFlowHandler method running")
        countryAdapter = CountryCoverFlowAdapter(countryDataList, requireContext())
        countryImageCoverflow.adapter = countryAdapter

        countryImageCoverflow.setOnScrollPositionListener(object :
            FeatureCoverFlow.OnScrollPositionListener {
            override fun onScrolling() {
                //implemented method
            }

            override fun onScrolledToPosition(position: Int) {
                countryName.setText(countryDataList[position].countryName)
            }
        })

        countryImageCoverflow.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            val selectedId: Long = adapterView.selectedItemId
            view1.setBackgroundColor(Color.LTGRAY)
            if (selectedId == 2L) {
                sharePreferencesEditorCountryId.putInt(
                    "countryId",
                    countryDataList[i - 2].countryId.toInt()
                )
                sharePreferencesEditorCountryId.commit()
                view?.findNavController()
                    ?.navigate(R.id.action_countriesDisplayFragment_to_competitionsDataFragmant)
            } else {
                sharePreferencesEditorCountryId.putInt(
                    "countryId",
                    countryDataList[i].countryId.toInt()
                )
                sharePreferencesEditorCountryId.commit()
                view?.findNavController()
                    ?.navigate(R.id.action_countriesDisplayFragment_to_competitionsDataFragmant)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.countryDataProcess().removeObservers(this)
        Log.d("CountriesDisplayFrag", "onPause() is running")
    }

    override fun onStop() {
        super.onStop()
        Log.d("CountriesDisplayFrag", "onStop() is running")
    }
}
