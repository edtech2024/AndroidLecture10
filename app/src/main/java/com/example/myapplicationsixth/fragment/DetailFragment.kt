package com.example.myapplicationsixth.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.domain.usecase.UseCase
import com.example.myapplicationsixth.ItemApplication
import com.example.myapplicationsixth.databinding.FragmentDetailBinding
import com.example.myapplicationsixth.viewmodel.DetailViewModel
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
        private const val UID = "Uid"
        private const val FREQUENCY = "Frequency"
        private const val COLOR = "Color"
        private const val DATE = "Date"
        private const val DONE_DATES = "Done_dates"

        fun newInstance(
            action: String?, id: Int, uid: String?,
            title: String?, description: String?,
            priority: String?, type: String?,
            count: String?,
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

        initializationComposeViewDetail()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializationButton()
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


    /*private fun initializationButton(){

        binding.btnSave.setOnClickListener(){
            detailViewModel.touchButton()

            onSaveClicked()
        }
    }*/

    private fun initializationComposeViewDetail() {
        binding.composeViewDetail.apply {
            // Dispose of the Composition when the view's LifecycleOwner is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Surface(modifier = Modifier.fillMaxSize()) {
                    //Navigation()
                    DetailScreen()
                }
            }
        }
    }

    @Composable
    //fun DetailScreen(modifier: Modifier = Modifier, navController: NavController) {
    fun DetailScreen(modifier: Modifier = Modifier) {
        Row(modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black)
            .padding(10.dp)
            //.clickable { navController.navigate("first") }
        ) {

            Box(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val id = detailViewModel.id.collectAsState()
                    val uid = detailViewModel.uid.collectAsState()
                    val title = detailViewModel.title.collectAsState()
                    val description = detailViewModel.description.collectAsState()
                    val priority = detailViewModel.priority.collectAsState()
                    val type = detailViewModel.type.collectAsState()
                    val count = detailViewModel.count.collectAsState()
                    val frequency = detailViewModel.frequency.collectAsState()
                    val color = detailViewModel.color.collectAsState()
                    val date = detailViewModel.date.collectAsState()
                    val done_dates =  detailViewModel.color.collectAsState()

                    Text(text = "Detail screen")

                    OutlinedTextField(
                        value = id.value,
                        onValueChange = { detailViewModel.setId(it) },
                        label = { Text(text = "id") }
                    )

                    OutlinedTextField(
                        value = uid.value,
                        onValueChange = { detailViewModel.setUid(it) },
                        label = { Text(text = "uid") }
                    )

                    OutlinedTextField(
                        value = title.value,
                        onValueChange = { detailViewModel.setTitle(it) },
                        label = { Text(text = "title") }
                    )

                    OutlinedTextField(
                        value = description.value,
                        onValueChange = { detailViewModel.setDescription(it) },
                        label = { Text(text = "description") }
                    )

                    OutlinedTextField(
                        value = priority.value,
                        onValueChange = { detailViewModel.setPriority(it) },
                        label = { Text(text = "priority") }
                    )

                    OutlinedTextField(
                        value = type.value,
                        onValueChange = { detailViewModel.setType(it) },
                        label = { Text(text = "type") }
                    )

                    OutlinedTextField(
                        value = count.value,
                        onValueChange = { detailViewModel.setCount(it) },
                        label = { Text(text = "count") }
                    )

                    OutlinedTextField(
                        value = frequency.value,
                        onValueChange = { detailViewModel.setFrequency(it) },
                        label = { Text(text = "frequency") }
                    )

                    OutlinedTextField(
                        value = color.value,
                        onValueChange = { detailViewModel.setColor(it) },
                        label = { Text(text = "color") }
                    )

                    OutlinedTextField(
                        value = date.value,
                        onValueChange = { detailViewModel.setDate(it) },
                        label = { Text(text = "date") }
                    )

                    OutlinedTextField(
                        value = done_dates.value,
                        onValueChange = { detailViewModel.setDone_dates(it) },
                        label = { Text(text = "done_dates") }
                    )

                    // call vm.method
                    Button(onClick = {
                        detailViewModel.touchButton()
                        onSaveClicked()
                    }) {
                        Text(text = "save")
                    }
                }
            }
        }
    }

    sealed class Screen(var route: String) {
        object Detail : Screen("detail")
    }
}