package com.example.abhinav.milkcalc.activities;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhinav.milkcalc.R;
import com.example.abhinav.milkcalc.databinding.ActivityAddLogBinding;
import com.example.abhinav.milkcalc.fragments.DatePickerFragment;
import com.example.abhinav.milkcalc.pojo.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class AddLogActivity extends AppCompatActivity
        implements DatePickerFragment.OnDateSelectedListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_log);

        log = (Log) getIntent().getSerializableExtra("EXTRA_LOG");
        if (log == null) log = new Log();
        binding.setLog(log);

        // Get logs ref
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        logsRef = database.getReference("logs");

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> fromAdapter = ArrayAdapter.createFromResource(this,
                R.array.from_diary_location_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> toAdapter = ArrayAdapter.createFromResource(this,
                R.array.to_diary_location_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapters to the spinner
        binding.fromDairySpinner.setAdapter(fromAdapter);
        binding.toDairySpinner.setAdapter(toAdapter);

        if (!TextUtils.isEmpty(log.fromDairy)) {
            int spinnerPosition = fromAdapter.getPosition(log.fromDairy);
            binding.fromDairySpinner.setSelection(spinnerPosition);
        }

        if (!TextUtils.isEmpty(log.toDairy)) {
            int spinnerPosition = fromAdapter.getPosition(log.toDairy);
            binding.toDairySpinner.setSelection(spinnerPosition);
        }

        // Set listeners
        binding.fromDairySpinner.setOnItemSelectedListener(onFromDairySelected);
        binding.toDairySpinner.setOnItemSelectedListener(onToDairySelected);
        binding.btnAdd.setOnClickListener(onClickButtonAdd);
        binding.date.setOnClickListener(onClickDate);
    }

    @Override
    public void onDateSelected(Date date) {
        binding.date.setText(String.format("%s", date));
    }


    private ActivityAddLogBinding binding;
    private Log log;
    private DatabaseReference logsRef;

    private View.OnClickListener onClickButtonAdd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(AddLogActivity.this, "Saved", Toast.LENGTH_SHORT).show();

            if (TextUtils.isEmpty(log.serverID)) {
                // No serverId this means its a new entry
                logsRef.push().setValue(log);
            } else {
                // Update existing item
                logsRef.child(log.serverID).setValue(log);
            }
            finish();
        }
    };

    private View.OnClickListener onClickDate = v -> {
        DatePickerFragment pickerFragment = new DatePickerFragment();
        pickerFragment.setOnDateSelectedListener(AddLogActivity.this);
        pickerFragment.show(getFragmentManager(), "datePicker");
    };

    private AdapterView.OnItemSelectedListener onFromDairySelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            log.setFromDairy(String.valueOf(textView.getText()));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener onToDairySelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            log.setToDairy(String.valueOf(textView.getText()));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
