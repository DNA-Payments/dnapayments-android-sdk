package com.dna.sdk.demo.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.dna.sdk.demo.R;
import com.dna.sdk.dnapayments.api.ApiResponse;
import com.dna.sdk.dnapayments.domain.AuthInteractor;
import com.dna.sdk.dnapayments.models.network.AuthToken;
import com.dna.sdk.dnapayments.utils.DnaUtilsKt;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

public class ExampleInJavaFragment extends Fragment {

    public ExampleInJavaFragment() {
        // Required empty public constructor
    }

    public static ExampleInJavaFragment newInstance() {
        ExampleInJavaFragment fragment = new ExampleInJavaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_java, container, false);

        sendRequest();
        return view;
    }

    public void sendRequest() {
        AuthInteractor.Companion.getInstance().getUserToken("1", new BigDecimal(10.00), "GBP", "", DnaUtilsKt.getContinuation(
                (tokenResult, throwable) -> {
                    System.out.println("Coroutines finished");
                    System.out.println("Result: " + tokenResult);
                    System.out.println("Exception: " + throwable);
                }
        ));

    }

}