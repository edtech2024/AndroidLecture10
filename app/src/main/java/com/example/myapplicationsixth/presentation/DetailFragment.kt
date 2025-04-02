package com.example.myapplicationsixth.presentation

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationsixth.ItemApplication
import com.example.myapplicationsixth.data.ItemRepository
import com.example.myapplicationsixth.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    // Creates a new fragment given parameters
    // DetailFragment.newInstance()
    companion object {

        private const val ACTION = "Action"
        private const val ID = "Id"
        private const val TITLE = "Title"
        private const val DESCRIPTION = "Description"
        private const val PRIORITY = "Priority"
        private const val TYPE = "TYPE"
        private const val COUNT = "Count"
        private const val PERIOD = "Period"

        fun newInstance(
            action: String?, id: Int,
            title: String?, description: String?,
            priority: String?, type: String?,
            count: String?, period: String?,
        ): DetailFragment {
            val fragmentDetail = DetailFragment()
            val args = Bundle()

            args.putString(ACTION, action)
            args.putInt(ID, id)
            args.putString(TITLE, title)
            args.putString(DESCRIPTION, description)
            args.putString(PRIORITY, priority)
            args.putString(TYPE, type)
            args.putString(COUNT, count)
            args.putString(PERIOD, period)

            fragmentDetail.arguments = args
            return fragmentDetail
        }
    }

    // Define the events that the fragment will use to communicate
    interface OnItemCreateUpdateListener{
        // This can be any number of events to be sent to the activity
        fun onCreateItem()

        fun onUpdateItem()
    }

    // Define the listener of the interface type
    // listener will the activity instance containing fragment
    private var listenerCreateUpdate: OnItemCreateUpdateListener? = null

    lateinit var detailViewModel: DetailViewModel

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listenerCreateUpdate = context as OnItemCreateUpdateListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailViewModel = ViewModelProvider(this , object: ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DetailViewModel((activity?.application as ItemApplication).repository, getArguments(), listenerCreateUpdate) as T
            }
        }
        ).get(DetailViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentDetailBinding.inflate( inflater, container, false)
        _binding!!.lifecycleOwner = viewLifecycleOwner
        _binding!!.detailViewModel = detailViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializationButton()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDetach() {
        super.onDetach()
        listenerCreateUpdate = null
    }

    // Now we can fire the event when the user selects something in the fragment
    private fun onCreateClicked(){
        listenerCreateUpdate?.onCreateItem()
    }

    // Now we can fire the event when the user selects something in the fragment
    private fun onUpdateClicked() {
        listenerCreateUpdate?.onUpdateItem()
    }

    private fun initializationButton(){

        binding.btnSave.setOnClickListener(){
            detailViewModel.callClick()
            if (detailViewModel.argAction == "Create"){
                onCreateClicked()
            } else {
                onUpdateClicked()
            }
        }
    }

}