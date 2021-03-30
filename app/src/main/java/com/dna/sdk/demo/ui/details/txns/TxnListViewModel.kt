package com.dna.sdk.demo.ui.details.txns

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dna.sdk.demo.compose.DispatchViewModel
import com.dna.sdk.demo.preferences.TxnData
import com.dna.sdk.demo.utils.Event
import com.dna.sdk.demo.utils.RecyclerItemClickCallback

class TxnListViewModel : DispatchViewModel() {

    private val _goToDetails = MutableLiveData<Event<TxnData>>()
    val goToDetails: LiveData<Event<TxnData>> get() = _goToDetails

    val onRecyclerViewItemClickListener = object : RecyclerItemClickCallback {
        override fun onItemClick(any: Any) {
            when (any) {
                is TxnData -> _goToDetails.postValue(Event(any))
            }
        }
    }
}
