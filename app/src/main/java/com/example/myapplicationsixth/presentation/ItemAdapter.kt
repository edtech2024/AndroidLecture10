package com.example.myapplicationsixth.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationsixth.databinding.ItemListBinding
import com.example.myapplicationsixth.domain.Item


class ItemAdapter(
    private var onClicked: (Item) -> Unit
) : ListAdapter<Item, ItemAdapter.ItemViewHolder>(DiffCallback()) {

    inner class ItemViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {

            binding.tvItemId.text = item.id.toString()
            binding.tvItemTitle.text =  item.title.toString()
            binding.tvItemDescription.text = item.description.toString()
            binding.tvItemPriority.text = item.priority.toString()
            binding.tvItemType.text = item.type.toString()
            binding.tvItemCount.text = item.count.toString()
            binding.tvItemPeriod.text = item.period.toString()

            binding.root.setOnClickListener {
                onClicked(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val itemBinding = ItemListBinding.inflate(inflater, parent, false)
        // Return a new holder instance
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        viewHolder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class DiffCallback : DiffUtil.ItemCallback<Item>() {

        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
}