package com.dna.sdk.demo.ui.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import com.dna.sdk.demo.DemoApp
import com.dna.sdk.demo.R
import com.dna.sdk.demo.compose.ViewModelFragment
import com.dna.sdk.demo.databinding.FragmentSettingsBinding
import com.dna.sdk.demo.preferences.Preferences
import org.koin.android.ext.android.inject

class SettingsFragment : ViewModelFragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var viewModel: SettingsViewModel
    private val preferences: Preferences by inject()

    private val terminalList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(SettingsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(
            inflater,
            R.layout.fragment_settings, container
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnAdd.setOnClickListener {
            showTerminalDialog()
        }

        binding.btnSave.setOnClickListener {
            val selectedTerminal = binding.spTerminal.selectedItem.toString()
            preferences.setTerminalId(selectedTerminal)
            DemoApp().setupDnaSdk()
            showToast(requireContext(), "Successfully saved")
        }

        if (preferences.getTerminalIds().isEmpty()) {
            terminalList.addAll(resources.getStringArray(R.array.terminal_array))
        } else {
            terminalList.addAll(preferences.getTerminalIds())
        }

        populateTerminalSpinner()
        setSelectedTerminal()

        return binding.root
    }

    private fun setSelectedTerminal() {
        if (preferences.getTerminalId().isBlank()) {
            preferences.setTerminalId(terminalList[0])
            binding.spTerminal.setSelection(0)
        } else {
            for ((index, value) in terminalList.withIndex()) {
                if (value == preferences.getTerminalId()) {
                    binding.spTerminal.setSelection(index)
                    break
                }
            }
        }
    }

    private fun initObservers() {

    }

    private fun populateTerminalSpinner() {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            terminalList
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spTerminal.adapter = adapter
        }
        binding.spTerminal.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val selection = parent.getItemAtPosition(pos) as String
        when (parent.id) {
            R.id.sp_terminal -> {
                binding.spTerminal.setSelection(pos)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    private fun showTerminalDialog() {
        val dialogBuilder = AlertDialog.Builder(context).create()
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.layout_alert_dialog_terminal, null)

        val editText = dialogView.findViewById<View>(R.id.edt_terminal) as EditText
        val buttonSubmit: Button = dialogView.findViewById<View>(R.id.buttonSubmit) as Button
        val buttonCancel: Button = dialogView.findViewById<View>(R.id.buttonCancel) as Button

        buttonSubmit.setOnClickListener {
            val terminalId = editText.text.toString()

            if (terminalId.isBlank()) {
                showToast(requireContext(), "Can't be empty")
            } else {
                addAndSaveTerminalId(terminalId)
                dialogBuilder.dismiss()
            }
        }
        buttonCancel.setOnClickListener {
            dialogBuilder.dismiss()
        }

        dialogBuilder.setView(dialogView)
        dialogBuilder.show()
    }

    private fun addAndSaveTerminalId(terminalId: String) {
        terminalList.add(0, terminalId)
        populateTerminalSpinner()
        preferences.setTerminalIds(terminalList)
        showToast(requireContext(), "Successfully added")
    }
}