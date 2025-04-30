package com.example.myapplicationsixth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.UseCase
import com.example.myapplicationsixth.databinding.FragmentDetailBinding
import javax.inject.Inject

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
        private const val UID = "Uid"
        private const val FREQUENCY = "Frequency"
        private const val COLOR = "Color"
        private const val DATE = "Date"
        private const val DONE_DATES = "Done_dates"

        fun newInstance(
            action: String?, id: Int, uid: String?,
            title: String?, description: String?,
            priority: String?, type: String?,
            count: String?, period: String?,
            frequency: String?, color: String?, date: String?, done_dates: String?
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
            args.putString(UID, uid)
            args.putString(FREQUENCY, frequency)
            args.putString(COLOR, color)
            args.putString(DATE, date)
            args.putString(DONE_DATES, done_dates)

            fragmentDetail.arguments = args
            return fragmentDetail
        }
    }

    // Define the events that the fragment will use to communicate
    interface OnItemCreateUpdateListener{
        // This can be any number of events to be sent to the activity
        fun onSaveItem()
    }

    // Define the listener of the interface type
    // listener will the activity instance containing fragment
    private var listenerCreateUpdate: OnItemCreateUpdateListener? = null

    lateinit var detailViewModel: DetailViewModel

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var useCase: UseCase

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listenerCreateUpdate = context as OnItemCreateUpdateListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as ItemApplication).appComponent.inject(this)

        detailViewModel = ViewModelProvider(this , object: ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DetailViewModel(useCase, getArguments(), listenerCreateUpdate) as T
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
    private fun onSaveClicked() {
        listenerCreateUpdate?.onSaveItem()
    }


    private fun initializationButton(){

        binding.btnSave.setOnClickListener(){
            detailViewModel.touchButton()

            onSaveClicked()
        }
    }

}