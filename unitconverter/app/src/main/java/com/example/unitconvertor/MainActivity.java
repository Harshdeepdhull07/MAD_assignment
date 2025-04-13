package com.example.unitconvertor;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText inputValue;
    Spinner unitSpinner;
    TextView feetResult, inchResult, cmResult, meterResult, yardResult;

    String[] units = {"Feet", "Inches", "Centimeters", "Meters", "Yards"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link views
        inputValue = findViewById(R.id.inputValue);
        unitSpinner = findViewById(R.id.unitSpinner);
        feetResult = findViewById(R.id.feetResult);
        inchResult = findViewById(R.id.inchResult);
        cmResult = findViewById(R.id.cmResult);
        meterResult = findViewById(R.id.meterResult);
        yardResult = findViewById(R.id.yardResult);

        // Spinner adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);

        // Input text change listener
        inputValue.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                convert();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // Spinner selection change listener
        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                convert();
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    // Conversion logic
    void convert() {
        String inputStr = inputValue.getText().toString();
        if (inputStr.isEmpty()) {
            clearResults();
            return;
        }

        double input = Double.parseDouble(inputStr);
        String unit = unitSpinner.getSelectedItem().toString();

        double inMeters = 0;

        switch (unit) {
            case "Feet":
                inMeters = input * 0.3048;
                break;
            case "Inches":
                inMeters = input * 0.0254;
                break;
            case "Centimeters":
                inMeters = input / 100.0;
                break;
            case "Meters":
                inMeters = input;
                break;
            case "Yards":
                inMeters = input * 0.9144;
                break;
        }

        // Update results
        feetResult.setText("Feet: " + String.format("%.4f", inMeters / 0.3048));
        inchResult.setText("Inches: " + String.format("%.4f", inMeters / 0.0254));
        cmResult.setText("Centimeters: " + String.format("%.4f", inMeters * 100));
        meterResult.setText("Meters: " + String.format("%.4f", inMeters));
        yardResult.setText("Yards: " + String.format("%.4f", inMeters / 0.9144));
    }

    void clearResults() {
        feetResult.setText("Feet: ");
        inchResult.setText("Inches: ");
        cmResult.setText("Centimeters: ");
        meterResult.setText("Meters: ");
        yardResult.setText("Yards: ");
    }
}