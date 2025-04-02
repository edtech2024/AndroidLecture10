package com.example.myapplicationsixth.presentation

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationsixth.domain.Item


@BindingAdapter("itemsListType1")
fun setItemsList1(recyclerView: RecyclerView, itemsListType1: List<Item>?) {

    val itemAdapter: ItemAdapter = recyclerView.adapter as ItemAdapter

    if (itemsListType1 != null) {
        itemAdapter.submitList(itemsListType1.toMutableList())
    }

}