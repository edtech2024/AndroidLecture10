package com.example.myapplicationsixth.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myapplicationsixth.R
import com.example.myapplicationsixth.databinding.BsdFragmentBinding
import com.example.myapplicationsixth.viewmodel.ListViewModel
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

        initializationEditTextSearch()
        initializationBtnSortReset()
    }

    fun initializationEditTextSearch() {
        binding.etSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                listViewModel.applyFilterMethod()
            }
        })
    }

    fun initializationBtnSortReset() {
        var flagSort: Boolean = false

        binding.btnSort.setOnClickListener() {
            if (flagSort != true) {
                flagSort = true
                binding.btnSort.text = getString(R.string.reset)
                listViewModel.applySortMethod()

            } else {
                flagSort = false
                binding.btnSort.text = getString(R.string.sort)
                listViewModel.applyResetFilter()
            }
        }
    }

}