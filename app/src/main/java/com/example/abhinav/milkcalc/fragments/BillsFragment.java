package com.example.abhinav.milkcalc.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhinav.milkcalc.adapters.BillsAdapter;
import com.example.abhinav.milkcalc.database.contentProviders.BillsContentProvider;
import com.example.abhinav.milkcalc.database.tables.BillsTable;
import com.example.abhinav.milkcalc.databinding.FragmentBillsBinding;

public class BillsFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_BILLS = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBillsBinding.inflate(inflater, container, false);

        // initialize components
        getLoaderManager().initLoader(LOADER_BILLS, null, this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new BillsAdapter(null);
        binding.recyclerView.setAdapter(adapter);

        binding.recyclerView.setLayoutManager(
                new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_BILLS:
                return new CursorLoader(getContext(),
                        BillsContentProvider.CONTENT_URI,
                        BillsTable.Query.PROJECTION,
                        null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_BILLS:
                adapter.changeCursor(data);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private BillsAdapter adapter;
    private FragmentBillsBinding binding;
}
