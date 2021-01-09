package com.example.footballinfo.view.fragments


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.example.footballinfo.R
import com.example.footballinfo.database.DatabaseGetter
import com.example.footballinfo.viewmodels.TitleFragmentVMFactory
import com.example.footballinfo.viewmodels.TitleFragmentViewModel
import kotlinx.android.synthetic.main.fragment_title.*


class TitleFragment : Fragment() {

    private lateinit var topAnim: Animation
    private lateinit var botAnim: Animation

    private lateinit var viewModel: TitleFragmentViewModel
    private lateinit var viewModelFactory: TitleFragmentVMFactory

    lateinit var sharedPref: SharedPreferences
    lateinit var sharedPrefEditior: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        sharedPrefEditior = sharedPref.edit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("TitleFragment", "onCreateView running")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_title, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = TitleFragmentVMFactory(DatabaseGetter.getInstance(requireContext()))
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(TitleFragmentViewModel::class.java)

        val flag = sharedPref.getInt("flagHolder", 0)
        if (flag == 1) {
            viewModel.deleteCachedData()
            sharedPrefEditior.putInt("flagHolder", 0)
            sharedPrefEditior.commit()
        }

        Handler().postDelayed({
            val ext = FragmentNavigatorExtras(imageView to "logo_image", textView2 to "logo_text")
            view?.findNavController()?.navigate(
                R.id.action_titleFragment_to_countriesDisplayFragment, null,
                NavOptions.Builder().setPopUpTo(R.id.titleFragment, true).build(), ext
            )
        }, 100)
    }


    //Below Code for animation
    /* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)

         topAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_top_title)
         botAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_bot_title)

         imageView.animation = topAnim
         textView2.animation = botAnim
         textView.animation = botAnim
     }*/
}