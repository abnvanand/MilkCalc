package com.example.abhinav.milkcalc.activities;


import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhinav.milkcalc.R;
import com.example.abhinav.milkcalc.database.tables.BillsTable;
import com.example.abhinav.milkcalc.databinding.ActivityAddBillBinding;
import com.example.abhinav.milkcalc.fragments.DatePickerFragment;
import com.example.abhinav.milkcalc.pojo.Bill;

import java.util.Date;

import timber.log.Timber;

public class AddBillActivity extends AppCompatActivity
        implements DatePickerFragment.OnDateSelectedListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_bill);
        bill = new Bill();
        binding.setBill(bill);

        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> fromAdapter = ArrayAdapter.createFromResource(this,
                R.array.from_diary_location_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> toAdapter = ArrayAdapter.createFromResource(this,
                R.array.to_diary_location_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the fromAdapter to the spinner
        binding.fromDairySpinner.setAdapter(fromAdapter);
        binding.toDairySpinner.setAdapter(toAdapter);

        binding.fromDairySpinner.setOnItemSelectedListener(onFromDairySelected);
        binding.toDairySpinner.setOnItemSelectedListener(onToDairySelected);

        binding.btnAdd.setOnClickListener(onClickButtonAdd);
        binding.date.setOnClickListener(onClickDate);
    }

    @Override
    public void onDateSelected(Date date) {
        binding.date.setText(String.format("%s", date));
    }


    private ActivityAddBillBinding binding;
    private Bill bill;

    private View.OnClickListener onClickButtonAdd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(AddBillActivity.this, bill.toString(), Toast.LENGTH_SHORT).show();
            finish();
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

    private AdapterView.OnItemSelectedListener onFromDairySelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            bill.setFromDairy(String.valueOf(textView.getText()));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener onToDairySelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            bill.setToDairy(String.valueOf(textView.getText()));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
