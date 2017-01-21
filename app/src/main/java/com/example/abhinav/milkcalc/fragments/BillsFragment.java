package com.example.abhinav.milkcalc.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhinav.milkcalc.adapters.BillsAdapter;
import com.example.abhinav.milkcalc.databinding.FragmentBillsBinding;

public class BillsFragment extends Fragment {

    private static final int LOADER_BILLS = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        adapter = new BillsAdapter(null);
//        binding.recyclerView.setAdapter(adapter);

        binding.recyclerView.setLayoutManager(
                new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private BillsAdapter adapter;
    private FragmentBillsBinding binding;
}
