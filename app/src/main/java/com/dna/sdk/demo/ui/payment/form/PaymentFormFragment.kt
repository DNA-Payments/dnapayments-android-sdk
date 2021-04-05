package com.dna.sdk.demo.ui.payment.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.dna.sdk.demo.R
import com.dna.sdk.demo.compose.ViewModelFragment
import com.dna.sdk.demo.databinding.FragmentPaymentFormBinding

class PaymentFormFragment : ViewModelFragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentPaymentFormBinding
    private lateinit var viewModel: PaymentFormViewModel

    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(PaymentFormViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val args = PaymentFormFragmentArgs.fromBundle(it)
            token = args.token
            viewModel.selectedCurrency = args.currency
            viewModel.amount = args.amount.toDouble()
            viewModel.token = token
            viewModel.invoiceId = args.invoiceId
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = binding(
            inflater,
            R.layout.fragment_payment_form, container
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        populateMonthArray()
        populateYearArray()
        populatePeriodSpinner()
        populateRecurringTransactionSpinner()
        populateTransactionSpinner()

        initObservers()
        return binding.root
    }

    private fun initObservers() {
        viewModel.loadPaymentsLiveData.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { paymentData ->
                if(paymentData.threeD?.acsUrl == null) {
                    viewModel.showErrorMessage("acsUrl not returned")
                } else {
                    val directions =
                        PaymentFormFragmentDirections.actionPaymentFormFragmentToPaymentWebFragment(
                            paymentData = paymentData, token = token
                        )
                    viewModel.progress.set(false)
                    findNavController().navigate(directions)
                }
            }
        })

        viewModel.onErrorLiveData.observe(viewLifecycleOwner, Observer {
            showToast(requireContext(), it)
        })
    }

    private fun populateTransactionSpinner() {
        binding.spTransactionType.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.transaction_type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spTransactionType.adapter = adapter
        }
    }

    private fun populateRecurringTransactionSpinner() {
        binding.spRecurringTransactionType.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.recurring_trans_type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spRecurringTransactionType.adapter = adapter
        }
    }

    private fun populatePeriodSpinner() {
        binding.spPeriodicType.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.periodic_type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spPeriodicType.adapter = adapter
        }
    }

    private fun populateMonthArray() {
        binding.spMonth.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.month_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spMonth.adapter = adapter
        }
    }

    private fun populateYearArray() {
        binding.spYear.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.year_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spYear.adapter = adapter
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val selection = parent.getItemAtPosition(pos) as String
        when (parent.id) {
            R.id.sp_month -> {
                viewModel.selectedMonth = selection
            }
            R.id.sp_year -> {
                viewModel.selectedYear = selection
            }
            R.id.sp_periodic_type -> {
                if (pos != 0) {
                    viewModel.selectedPeriodicType = selection
                } else {
                    viewModel.selectedPeriodicType = ""
                }
            }
            R.id.sp_recurring_transaction_type -> {
                if (pos != 0) {
                    viewModel.selectedRecurringTxnType = selection
                } else {
                    viewModel.selectedRecurringTxnType = ""
                }
            }
            R.id.sp_transaction_type -> {
                if(pos != 0) {
                    viewModel.selectedTxnType = selection
                } else {
                    viewModel.selectedTxnType = ""
                }
            }
        }
    }
}