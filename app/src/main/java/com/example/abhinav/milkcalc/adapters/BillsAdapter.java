package com.example.abhinav.milkcalc.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhinav.milkcalc.BR;
import com.example.abhinav.milkcalc.R;
import com.example.abhinav.milkcalc.databinding.ItemBillBinding;
import com.example.abhinav.milkcalc.pojo.Bill;

import java.util.ArrayList;

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.ViewHolder> {
    private ArrayList<Bill> bills;

    public BillsAdapter(ArrayList<Bill> bills) {
        this.bills = bills;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bill, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bill bill = bills.get(position);
        ViewDataBinding holderBinding = holder.getBinding();
        holderBinding.setVariable(BR.bill, bill);
        holderBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return bills.size();
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
