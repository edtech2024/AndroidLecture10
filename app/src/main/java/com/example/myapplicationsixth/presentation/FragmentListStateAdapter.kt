package com.example.myapplicationsixth.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentListStateAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val TYPE_1 = "Type 1"
    private val TYPE_2 = "Type 2"
    private val ERROR = "Error"

    // возвращает количество страниц, которые будут в ViewPager2
    override fun getItemCount(): Int = 2

    // по номеру страницы, передаваемому в качестве параметра position, возвращает объект фрагмента
    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> ListFragmentType1.newInstance(TYPE_1)
            1 -> ListFragmentType2.newInstance(TYPE_2)
            else ->  throw Exception(ERROR)
        }

    }

}