package com.dna.sdk.demo.ui.details.txns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dna.sdk.demo.R
import com.dna.sdk.demo.compose.ViewModelFragment
import com.dna.sdk.demo.databinding.FragmentTxnListBinding
import com.dna.sdk.demo.preferences.Preferences
import com.dna.sdk.demo.preferences.TxnData
import org.koin.android.ext.android.inject

class TxnListFragment : ViewModelFragment() {
    private lateinit var binding: FragmentTxnListBinding
    private lateinit var viewModel: TxnListViewModel
    private val preferences: Preferences by inject()

    var objects: ArrayList<TxnData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(TxnListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        objects.clear()
        binding.recyclerView.isNestedScrollingEnabled = false
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val txnList = preferences.getAllTransactions().reversed()
        objects.addAll(txnList)
        binding.recyclerView.adapter =
            TxnAdapter(objects, viewModel.onRecyclerViewItemClickListener)

        if (txnList.isEmpty()) {
            showToast(requireContext(), "No transactions yet")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(
            inflater,
            R.layout.fragment_txn_list, container
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initObservers()
        return binding.root
    }

    private fun initObservers() {
        viewModel.goToDetails.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { txnData ->
                val directions =
                    TxnListFragmentDirections.actionTxnListFragmentToDetailsFragment(txnData.txnId)
                findNavController().navigate(directions)
            }
        })
    }

}