package com.dna.sdk.demo.ui.details.txns

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dna.sdk.demo.R
import com.dna.sdk.demo.databinding.ItemTxnBinding
import com.dna.sdk.demo.preferences.TxnData
import com.dna.sdk.demo.utils.RecyclerItemClickCallback

class TxnAdapter(var objects: List<Any>, var callback: RecyclerItemClickCallback) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val binding: ItemTxnBinding =
                    DataBindingUtil.inflate(inflater, R.layout.item_txn, parent, false)
                QueryViewHolder(binding)
            }
            else -> {
                throw IllegalStateException("Incorrect ViewType found")
            }
        }
    }

    inner class QueryViewHolder(var binding: ItemTxnBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun initContent(item: TxnData) {
            binding.tvTxnId.text = "TxnId: ${item.txnId}"
            binding.tvAmount.text = "Amount: ${item.amount}"
            binding.tvTime.text = "Date: ${item.time}"
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                callback.onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = objects.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_ITEM -> {
                val viewHolder: QueryViewHolder = holder as QueryViewHolder
                viewHolder.initContent(objects[position] as TxnData)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (objects[position]) {
            is TxnData -> VIEW_TYPE_ITEM
            else -> throw IllegalStateException("Incorrect ViewType found")
        }
    }

}
