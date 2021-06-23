package com.mitwpu.anaemiadetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class ResultsActivity extends AppCompatActivity implements View.OnClickListener {
    Intent fromLoadingResultsIntent;
    PersonData user_data;
    EditText resultsNameEditText, resultsAgeEditText, resultsGenderEditText, resultsSymptomsEditTextMultiLine;
    EditText resultsPercentageEditText, resultsClassEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_results);

        fromLoadingResultsIntent = getIntent();
        user_data = (PersonData) fromLoadingResultsIntent.getSerializableExtra("FormData");

        resultsNameEditText = (EditText) findViewById(R.id.resultsNameEditText);
        resultsAgeEditText = (EditText) findViewById(R.id.resultsAgeEditText);
        resultsGenderEditText = (EditText) findViewById(R.id.resultsGenderEditText);
        resultsSymptomsEditTextMultiLine = (EditText) findViewById(R.id.resultsSymptomsEditTextMultiLine);
        resultsPercentageEditText = (EditText) findViewById(R.id.resultsPercentageEditText);
        resultsClassEditText = (EditText) findViewById(R.id.resultsClassEditText);

        resultsNameEditText.setText(user_data.name);
        resultsAgeEditText.setText(user_data.age+" years");
        resultsGenderEditText.setText(user_data.sex);
        resultsSymptomsEditTextMultiLine.setText(user_data.symptoms);
        resultsPercentageEditText.setText(user_data.percentage+"%");
        resultsClassEditText.setText(user_data.result);

        final Button homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.homeButton) {
            Intent toMainIntent = new Intent(ResultsActivity.this, MainActivity.class);
            startActivity(toMainIntent);
        }
    }
}