package com.example.myapplicationsixth.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplicationsixth.databinding.FragmentAboutAppBinding


class AboutAppFragment : Fragment() {

    // Creates a new fragment given parameters
    // AboutAppFragment.newInstance()
    companion object {
        fun newInstance(): AboutAppFragment {
            val fragmentAboutApp = AboutAppFragment()
            return fragmentAboutApp
        }
    }

    private var _binding: FragmentAboutAppBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAboutAppBinding.inflate( inflater, container, false)
        return binding.root
    }

}
