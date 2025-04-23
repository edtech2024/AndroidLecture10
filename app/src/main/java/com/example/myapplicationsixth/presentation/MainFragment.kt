package com.example.myapplicationsixth.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myapplicationsixth.ItemApplication
import com.example.myapplicationsixth.R
import com.example.myapplicationsixth.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator


class MainFragment : Fragment() {

    // Creates a new fragment given parameters
    // MainFragment.newInstance()
    companion object {

        fun newInstance(): MainFragment {
            val fragmentMain = MainFragment()
            return fragmentMain
        }
    }

    // Define the events that the fragment will use to communicate
    interface OnItemAddListener{
        // This can be any number of events to be sent to the activity
        fun onAddItem(add: Bundle)
    }

    // Define the listener of the interface type
    // listener will the activity instance containing fragment
    private var listenerAdd: OnItemAddListener? = null

    lateinit var bsdFragment: BSDFragment

    var flag: Boolean = false

    lateinit var listViewModel: ListViewModel

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listenerAdd = context as MainFragment.OnItemAddListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listViewModel = ViewModelProvider(this, object: ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>,  extras: CreationExtras): T {
                return ListViewModel((activity?.application as ItemApplication).repository) as T
            }
        }
        ).get(ListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate( inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializationFAB()
        initializationViewPager()
        initializationBSD()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDetach() {
        super.onDetach()
        listenerAdd = null
    }

    private fun initializationViewPager(){
        // When requested, this adapter returns a ObjectFragment,
        // representing an object in the collection.
        binding.viewPager2.adapter = FragmentListStateAdapter(this, transferString() )

        TabLayoutMediator(binding.tabLayout, binding.viewPager2,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = getString(R.string.type) + "${(position + 1)}"
            }
        ).attach()
    }

    private fun transferString():String {
        val args: String =
            getString(R.string.type_1) + " " +
            getString(R.string.type_2) + " " +
            getString(R.string.error)
        return args
    }

    private fun initializationFAB(){
        // do something when the button is clicked
        binding.fab.setOnClickListener {
            // Passing the data to the DetailFragment
            val args = Bundle()
            args.putString(getString(R.string.action), getString(R.string.create))

            onAddClicked(args)
        }
    }

    // Now we can fire the event when the user selects something in the fragment
    fun onAddClicked(bundle: Bundle){
        listenerAdd?.onAddItem(bundle)
    }


    fun initializationBSD() {
        var flagOpen: Boolean = false

        binding.btnOpen.setOnClickListener() {
            if (flagOpen != true) {
                flagOpen = true
                binding.btnOpen.text = getString(R.string.close)
                openBSDFragment()
            }
            else {
                flagOpen = false
                binding.btnOpen.text = getString(R.string.open)
                closeBSDFragment()
            }
        }
    }

    fun openBSDFragment(){
        if (flag != true) {
            flag = true
            bsdFragment = BSDFragment.newInstance(getString(R.string.type_1))
            val fragmentTransaction: FragmentTransaction = childFragmentManager.beginTransaction()
            fragmentTransaction.replace(binding.containerBsd.id, bsdFragment)
            fragmentTransaction.commit()
        } else {
            flag = false
            bsdFragment = BSDFragment.newInstance(getString(R.string.type_2))
            val fragmentTransaction: FragmentTransaction = childFragmentManager.beginTransaction()
            fragmentTransaction.replace( binding.containerBsd.id, bsdFragment)
            fragmentTransaction.commit()
        }
    }

    fun closeBSDFragment(){
        bsdFragment.dismiss()
    }

}