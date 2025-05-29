package com.example.myapplicationsixth.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import androidx.navigation.compose.navigate
import com.example.domain.dataobject.Item
import com.example.domain.usecase.UseCase
import com.example.myapplicationsixth.adapter.FragmentListStateAdapter
import com.example.myapplicationsixth.ItemApplication
import com.example.myapplicationsixth.R
import com.example.myapplicationsixth.databinding.FragmentMainBinding
import com.example.myapplicationsixth.viewmodel.ListViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject


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
    interface OnItemAddEditListener{
        // This can be any number of events to be sent to the activity
        fun onAddItem(add: Bundle)

        fun onEditItem(edit: Bundle)
    }

    // Define the listener of the interface type
    // listener will the activity instance containing fragment
    private var listenerAdd: OnItemAddEditListener? = null

    lateinit var bsdFragment: BSDFragment

    var flag: Boolean = false

    lateinit var listViewModel: ListViewModel

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var useCase: UseCase

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listenerAdd = context as OnItemAddEditListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as ItemApplication).appComponent.inject(this)

        listViewModel = ViewModelProvider(this, object: ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>,  extras: CreationExtras): T {
                return ListViewModel(useCase) as T
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

        // ???
        initializationComposeViewMain()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializationFAB()
        //initializationViewPager()
        initializationBSD()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDetach() {
        super.onDetach()
        listenerAdd = null
    }

    private fun initializationComposeViewMain() {
        binding.composeViewMain.apply {
            // Dispose of the Composition when the view's LifecycleOwner is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Navigation()
                }
            }
        }
    }

    // ???
    // input function paste arg viewmodel
    @Composable
    fun Navigation() {
        val data1 = listViewModel.itemsListType1.observeAsState(emptyList())
        val data2 = listViewModel.itemsListType2.observeAsState(emptyList())

        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screen.FirstScreen.route){
            composable(Screen.FirstScreen.route){
                FirstScreen(navController = navController, data = data1, onClick = { item -> onItemClicked(item) } )
                //FirstScreen(data = data1, onClick = navController.navigate(MainFragment.Screen.Detail.route))
            }
            /*composable(MainFragment.Screen.SecondScreen.route){
                SecondScreen(navController = navController, data = data2)
            }*/
            composable(Screen.Detail.route){
                DetailScreen(navController = navController)
            }
            /*composable(Screen.Chat.route + "/{chatId}",
                arguments = listOf(navArgument("chatId") { type = NavType.IntType})) {
                    stackEntry ->
                ChatScreen(navController = navController, viewModel = ChatViewModel(),
                    id = stackEntry.arguments?.getInt("chatId")
                )
            }*/
        }

        ViewPagerScreen(navController, data1, data2)
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun ViewPagerScreen(navController: NavController,
                        data1: State<List<Item>>,
                        data2: State<List<Item>>
    ){
        /*
        // данные для отображения
        val data = listOf("iPhone 15 Pro", "Redmi Note 12 Pro+", "Galaxy S23 Ultra", "Infinix NOTE 30 Pro", "Honor 90")
        val dataEmpty = listOf("")
        val data1 = listViewModel.itemsListType1.observeAsState(emptyList())
        val data2 = listViewModel.itemsListType2.observeAsState(emptyList())
*/
        var state by remember { mutableStateOf(0) }
        val tabs = listOf("TAB 1", "TAB 2")
        //val pagerState = rememberPagerState(pageCount = 2, initialPage = 0)
        //initialPage = state
        //count = 2

        MaterialTheme {
            HorizontalPager(count = 2, modifier = Modifier.fillMaxHeight()) { page ->
                Column {
                    TabRow(selectedTabIndex = state) {
                        tabs.forEachIndexed { index, title ->
                            Tab(text = { Text(title) },
                                selected = state == index,
                                onClick = { state = index }
                            )
                        }
                    }
                    when (state) {
                        /*0 -> FirstScreen(data1, navController)
                        1 -> SecondScreen(data2, navController)*/
                        /*0 -> FirstScreen(data1, navController.navigate(MainFragment.Screen.Detail.route))
                        1 -> FirstScreen(data2, navController.navigate(MainFragment.Screen.Detail.route))*/
                        0 -> FirstScreen(data1, navController,  { item -> onItemClicked(item) } )
                        1 -> FirstScreen(data2, navController,  { item -> onItemClicked(item) } )
                    }
                }
            }
        }
    }

    sealed class Screen(var route: String) {
        object FirstScreen : Screen("first")
        object SecondScreen : Screen("second")
        object Detail : Screen("detail")
    }

    @Composable
    fun SecondScreen(data: State<List<Item>>, navController: NavController) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(data.value) {
                /*ListItem(it, onClick = {
                        //it -> onItemClicked(it)
                    navController.navigate("detail")
                })*/
                //ListItem(it)
                //ListItem(it, navController = navController)
            }
        }
    }

    //navController: NavController
    //onClick: () -> Unit
    @Composable
    fun FirstScreen(data: State<List<Item>>, navController: NavController, onClick: (Item) -> Unit) {
        Button(onClick = { navController.navigate(MainFragment.Screen.Detail.route) }){
            Text("Click", fontSize = 25.sp)
        }
        LazyColumn(Modifier.fillMaxSize()) {
            items(data.value) {
                /*ListItem(it, onClick = {
                        //it -> onItemClicked(it)
                    navController.navigate("detail")
                })*/
                //ListItem(it)
                ListItem(it, navController = navController, onClick = onClick)
                //ListItem(it, onClick = onClick)
            }
        }
    }

    @Composable
    //fun ListItem(data: Item, modifier: Modifier = Modifier, onClick: Unit) { // navigate
    fun ListItem(data: Item, modifier: Modifier = Modifier, navController: NavController, onClick: (Item) -> Unit) { // navigate
    //fun ListItem(data: Item, modifier: Modifier = Modifier, onClick: (Item) -> Unit) { // callback
    //fun ListItem(data: Item, modifier: Modifier = Modifier) { base
        Surface() {
            Row(modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = Color.Black)
                .padding(10.dp)
                .clickable {
                    onClick(data)
                    //navController.navigate("detail")
                    //navController.navigate(MainFragment.Screen.Detail.route)
                    //onClick
                    Toast.makeText(activity?.applicationContext, "Click", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text(text = data.title.toString())
                // … other composables required for displaying `data`
            }
        }
    }

    /*
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        ViewPagerScreen()
    }
    */

    @Composable
    fun DetailScreen(modifier: Modifier = Modifier, navController: NavController) {
        Row(modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black)
            .padding(10.dp)
            .clickable { navController.navigate("first") }
        ) {
            Text(text = "Detail Screen")
            // … other composables required for displaying `data`
        }
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

    // Now we can fire the event when the user selects something in the fragment
    private fun onEditClicked(bundle: Bundle){
        listenerAdd?.onEditItem(bundle)
    }

    private fun onItemClicked(item: Item){

        val args = Bundle()
        args.putString(getString(R.string.action), getString(R.string.edit))
        args.putInt(getString(R.string.id), item.id!!)
        args.putString(getString(R.string.title), item.title)
        args.putString(getString(R.string.description), item.description)
        args.putString(getString(R.string.priority), item.priority.toString())
        args.putString(getString(R.string.type), item.type.toString())
        args.putString(getString(R.string.count), item.count.toString())
        args.putString(getString(R.string.frequency), item.frequency!!.toString())
        args.putString(getString(R.string.uid), item.uid.toString())
        args.putString(getString(R.string.date), item.date!!.toString())
        args.putString(getString(R.string.done_dates), item.done_dates!!.toString())
        args.putString(getString(R.string.color), item.color!!.toString())

        onEditClicked(args)
    }

}