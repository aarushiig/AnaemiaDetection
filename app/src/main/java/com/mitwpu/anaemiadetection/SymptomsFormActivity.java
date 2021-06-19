package com.mitwpu.anaemiadetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class SymptomsFormActivity extends AppCompatActivity implements View.OnClickListener {
    PersonData user_data;
    Intent fromIDFIntent;
    RadioButton symptomsFatigueRadioButton, symptomsLightHeadednessRadioButton, symptomsDizzinessRradioButton, symptomsMalaiseRadioButton;
    RadioButton symptomsFastHeartRateRadioButton, symptomsPalpitationsRadioButton;
    RadioButton symptomsHeadacheRadioButton, symptomsWeaknessRadioButton, symptomsBrittleNailsRadioButton, symptomsPallorRadioButton, symptomsShortnessOfBreathRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_symptoms_form);

        fromIDFIntent = getIntent();
        user_data = (PersonData) fromIDFIntent.getSerializableExtra("FormData");

        symptomsFatigueRadioButton =(RadioButton) findViewById(R.id.symptomsFatigueRadioButton);
        symptomsLightHeadednessRadioButton =(RadioButton) findViewById(R.id.symptomsLightHeadednessRadioButton);
        symptomsDizzinessRradioButton =(RadioButton) findViewById(R.id.symptomsDizzinessRradioButton);
        symptomsMalaiseRadioButton =(RadioButton) findViewById(R.id.symptomsMalaiseRadioButton);
        symptomsFastHeartRateRadioButton =(RadioButton) findViewById(R.id.symptomsFastHeartRateRadioButton);
        symptomsPalpitationsRadioButton =(RadioButton) findViewById(R.id.symptomsPalpitationsRadioButton);
        symptomsHeadacheRadioButton =(RadioButton) findViewById(R.id.symptomsHeadacheRadioButton);
        symptomsWeaknessRadioButton =(RadioButton) findViewById(R.id.symptomsWeaknessRadioButton);
        symptomsBrittleNailsRadioButton =(RadioButton) findViewById(R.id.symptomsBrittleNailsRadioButton);
        symptomsPallorRadioButton =(RadioButton) findViewById(R.id.symptomsPallorRadioButton);
        symptomsShortnessOfBreathRadioButton =(RadioButton) findViewById(R.id.symptomsShortnessOfBreathRadioButton);



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
            StringBuffer symptoms=new StringBuffer();

            Intent toCaptureImagesIntent = new Intent(SymptomsFormActivity.this, CaptureImagesActivity.class);
            if (symptomsFatigueRadioButton.isChecked()) {
                symptoms.append(symptomsFatigueRadioButton.getText().toString());
            }
            if (symptomsLightHeadednessRadioButton.isChecked()) {
                symptoms.append(","+symptomsLightHeadednessRadioButton.getText().toString());
            }
            if (symptomsDizzinessRradioButton.isChecked()) {
                symptoms.append(","+symptomsDizzinessRradioButton.getText().toString());
            }
            if (symptomsMalaiseRadioButton.isChecked()) {
                symptoms.append(","+symptomsMalaiseRadioButton.getText().toString());
            }
            if (symptomsFastHeartRateRadioButton.isChecked()) {
                symptoms.append(","+symptomsFastHeartRateRadioButton.getText().toString());
            }
            if (symptomsPalpitationsRadioButton.isChecked()) {
                symptoms.append(","+symptomsPalpitationsRadioButton.getText().toString());
            }
            if (symptomsHeadacheRadioButton.isChecked()) {
                symptoms.append(","+symptomsHeadacheRadioButton.getText().toString());
            }
            if (symptomsWeaknessRadioButton.isChecked()) {
                symptoms.append(","+symptomsWeaknessRadioButton.getText().toString());
            }
            if (symptomsBrittleNailsRadioButton.isChecked()) {
                symptoms.append(","+symptomsBrittleNailsRadioButton.getText().toString());
            }
            if (symptomsPallorRadioButton.isChecked()) {
                symptoms.append(","+symptomsPallorRadioButton.getText().toString());
            }
            if (symptomsShortnessOfBreathRadioButton.isChecked()) {
                symptoms.append(","+symptomsShortnessOfBreathRadioButton.getText().toString());
            }

            user_data.setSymptoms(symptoms.toString());
//            Toast.makeText(getApplicationContext(),"Name:"+user_data.name+"\nAge:"+user_data.age+"\nSex:"+user_data.sex+"\nNo:"+user_data.mobileNo+"\nEmail:"+user_data.email+"\nSymptoms:"+user_data.symptoms,Toast.LENGTH_LONG).show();
            toCaptureImagesIntent.putExtra("FormData",user_data);
            startActivity(toCaptureImagesIntent);
        }
    }
}