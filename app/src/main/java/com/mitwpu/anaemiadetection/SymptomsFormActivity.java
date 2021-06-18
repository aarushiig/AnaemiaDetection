package com.mitwpu.anaemiadetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.Objects;

public class SymptomsFormActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_symptoms_form);

        final Button backToIDFFromSymptomsButton = (Button) findViewById(R.id.backToIDFFromSymptomsButton);
        backToIDFFromSymptomsButton.setOnClickListener(this);

        final Button advanceToCaptureImagesFromSymptomsButton = (Button) findViewById(R.id.advanceToCaptureImagesFromSymptomsButton);
        advanceToCaptureImagesFromSymptomsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backToIDFFromSymptomsButton) {
            Intent toIDFIntent = new Intent(SymptomsFormActivity.this, IdentificationDataFormActivity.class);
            startActivity(toIDFIntent);
        } else if (v.getId() == R.id.advanceToCaptureImagesFromSymptomsButton) {
            Intent toCaptureImagesIntent = new Intent(SymptomsFormActivity.this, CaptureImagesActivity.class);
            startActivity(toCaptureImagesIntent);
        }
    }
}