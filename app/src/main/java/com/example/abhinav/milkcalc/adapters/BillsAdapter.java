package com.example.abhinav.milkcalc.adapters;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhinav.milkcalc.BR;
import com.example.abhinav.milkcalc.R;
import com.example.abhinav.milkcalc.database.tables.BillsTable;
import com.example.abhinav.milkcalc.databinding.ItemBillBinding;
import com.example.abhinav.milkcalc.pojo.Bill;

public class BillsAdapter extends CursorRecyclerViewAdapter<BillsAdapter.ViewHolder> {

    public BillsAdapter(Cursor cursor) {
        super(cursor);
    }

    @Override
    public void onBindViewHolder(BillsAdapter.ViewHolder viewHolder, Cursor cursor) {
        Bill bill = BillsTable.from(viewHolder.getBinding().getRoot().getContext(), cursor);

        ViewDataBinding holderBinding = viewHolder.getBinding();
        holderBinding.setVariable(BR.bill, bill);
        holderBinding.executePendingBindings();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bill, parent, false);

        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemBillBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ItemBillBinding getBinding() {
            return binding;
        }
    }
}
