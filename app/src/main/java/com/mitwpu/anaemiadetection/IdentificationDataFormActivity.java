package com.mitwpu.anaemiadetection;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class IdentificationDataFormActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_identification_data_form);

        final Button backToMainFromIdfButton = (Button) findViewById(R.id.backToMainFromIDFButton);
        backToMainFromIdfButton.setOnClickListener(this);

        final Calendar calendar = Calendar.getInstance();
        final EditText idfDobEditText = (EditText) findViewById(R.id.idfDobEditText);
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            idfDobEditText.setText(simpleDateFormat.format(calendar.getTime()));
        };
        idfDobEditText.setOnClickListener(v -> new DatePickerDialog(IdentificationDataFormActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backToMainFromIDFButton) {
            Intent toMainIntent = new Intent(IdentificationDataFormActivity.this, MainActivity.class);
            startActivity(toMainIntent);
        }
    }

}