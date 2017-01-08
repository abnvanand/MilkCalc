package com.example.abhinav.milkcalc.activities;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.abhinav.milkcalc.R;
import com.example.abhinav.milkcalc.databinding.ActivityAddBillBinding;
import com.example.abhinav.milkcalc.fragments.DatePickerFragment;

import java.util.Date;

public class AddBillActivity extends AppCompatActivity
        implements DatePickerFragment.OnDateSelectedListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_bill);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> fromAdapter = ArrayAdapter.createFromResource(this,
                R.array.from_diary_location_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> toAdapter = ArrayAdapter.createFromResource(this,
                R.array.to_diary_location_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the fromAdapter to the spinner
        binding.fromDairySpinner.setAdapter(fromAdapter);
        binding.toDairySpinner.setAdapter(toAdapter);

        binding.btnAdd.setOnClickListener(onClickButtonAdd);
        binding.date.setOnClickListener(onClickDate);
    }

    @Override
    public void onDateSelected(Date date) {
        binding.date.setText(String.format("%s", date));
    }


    private ActivityAddBillBinding binding;
    private View.OnClickListener onClickButtonAdd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(AddBillActivity.this, "Added", Toast.LENGTH_SHORT).show();
        }
    };
    private View.OnClickListener onClickDate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerFragment pickerFragment = new DatePickerFragment();
            pickerFragment.setOnDateSelectedListener(AddBillActivity.this);
            pickerFragment.show(getFragmentManager(), "datePicker");
        }
    };
}
