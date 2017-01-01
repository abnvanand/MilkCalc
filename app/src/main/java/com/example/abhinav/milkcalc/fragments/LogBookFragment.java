package com.example.abhinav.milkcalc.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhinav.milkcalc.databinding.FragmentLogBookBinding;

public class LogBookFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLogBookBinding binding = FragmentLogBookBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
