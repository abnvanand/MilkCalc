package com.example.abhinav.milkcalc.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhinav.milkcalc.databinding.FragmentBillsBinding;

public class BillsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentBillsBinding binding = FragmentBillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
