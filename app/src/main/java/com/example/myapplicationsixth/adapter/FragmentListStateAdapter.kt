package com.example.myapplicationsixth.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplicationsixth.fragment.ListFragmentType1
import com.example.myapplicationsixth.fragment.ListFragmentType2


class FragmentListStateAdapter(fragment: Fragment, private val inputString: String) : FragmentStateAdapter(fragment) {

    private val args: List<String> = inputString.split(" ")
    private val TYPE_FRAGMENT_1: String = args[0]
    private val TYPE_FRAGMENT_2: String = args[1]
    private val ERROR_MESSAGE: String = args[2]

    // возвращает количество страниц, которые будут в ViewPager2
    override fun getItemCount(): Int = 2

    // по номеру страницы, передаваемому в качестве параметра position, возвращает объект фрагмента
    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> ListFragmentType1.newInstance(TYPE_FRAGMENT_1)
            1 -> ListFragmentType2.newInstance(TYPE_FRAGMENT_2)
            else ->  throw Exception(ERROR_MESSAGE)
        }

    }

}