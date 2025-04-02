package com.example.myapplicationsixth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myapplicationsixth.databinding.BsdFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BSDFragment : BottomSheetDialogFragment() {

    // Creates a new fragment given parameters
    // BSDFragment.newInstance()
    companion object {

        private const val TYPE = "Type"

        fun newInstance(type: String?): BSDFragment {
            val fragmentBSD = BSDFragment()
            val args = Bundle()
            args.putString(TYPE, type)
            fragmentBSD.arguments = args
            return fragmentBSD
        }

    }

    val listViewModel: ListViewModel by viewModels( ownerProducer = { requireParentFragment() })

    private var _binding: BsdFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = BsdFragmentBinding.inflate( inflater, container, false)
        _binding!!.lifecycleOwner = this
        _binding!!.listViewModel = listViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}