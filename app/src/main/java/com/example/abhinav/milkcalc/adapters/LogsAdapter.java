package com.example.abhinav.milkcalc.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abhinav.milkcalc.BR;
import com.example.abhinav.milkcalc.R;
import com.example.abhinav.milkcalc.databinding.ItemLogBinding;
import com.example.abhinav.milkcalc.pojo.Log;

import java.util.ArrayList;

public class LogsAdapter extends RecyclerView.Adapter<LogsAdapter.ViewHolder> {
    private ArrayList<Log> logs;

    public LogsAdapter(ArrayList<Log> logs) {
        this.logs = logs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_log, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log log = logs.get(position);
        ViewDataBinding holderBinding = holder.getBinding();
        holderBinding.setVariable(BR.log, log);
        holderBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemLogBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public ItemLogBinding getBinding() {
            return binding;
        }
    }
}
