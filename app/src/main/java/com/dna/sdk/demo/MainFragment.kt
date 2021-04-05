package com.dna.sdk.demo

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.dna.sdk.demo.compose.ViewModelFragment
import com.dna.sdk.demo.databinding.FragmentMainBinding


class MainFragment : ViewModelFragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(inflater, R.layout.fragment_main, container)

        binding.btnPayment.setOnClickListener {
            val directions = MainFragmentDirections.actionMainFragmentToPaymentFragment()
            findNavController().navigate(directions)
        }

        binding.btnTxns.setOnClickListener {
            val directions = MainFragmentDirections.actionMainFragmentToTxnListFragment()
            findNavController().navigate(directions)
        }

        binding.btnManualTxn.setOnClickListener {
            showTxnDialog()
        }

        /* binding.btnJava.setOnClickListener {
             val directions = MainFragmentDirections.actionMainFragmentToExampleInJavaFragment()
             findNavController().navigate(directions)
         }*/

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val directions = MainFragmentDirections.actionMainFragmentToSettingsFragment()
                findNavController().navigate(directions)
            }
        }
        return true
    }

    private fun showTxnDialog() {
        val dialogBuilder = AlertDialog.Builder(context).create()
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.layout_alert_dialog_txn, null)

        val editText = dialogView.findViewById<View>(R.id.edt_comment) as EditText
        val buttonSubmit: Button = dialogView.findViewById<View>(R.id.buttonSubmit) as Button
        val buttonCancel: Button = dialogView.findViewById<View>(R.id.buttonCancel) as Button

        buttonSubmit.setOnClickListener {
            val txnId = editText.text.toString()

            if (txnId.isBlank()) {
                showToast(requireContext(), "Can't be empty")
            } else {
                val directions = MainFragmentDirections.actionMainFragmentToDetailsFragment(txnId)
                findNavController().navigate(directions)
                dialogBuilder.dismiss()
            }
        }
        buttonCancel.setOnClickListener {
            dialogBuilder.dismiss()
        }

        dialogBuilder.setView(dialogView)
        dialogBuilder.show()
    }
}