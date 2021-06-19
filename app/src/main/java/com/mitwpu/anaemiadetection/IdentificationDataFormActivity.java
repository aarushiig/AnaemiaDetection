package com.mitwpu.anaemiadetection;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class IdentificationDataFormActivity extends AppCompatActivity implements View.OnClickListener {
    EditText idfDobEditText;
    EditText idfNameEditText;
    EditText idfMobileNoEditText;
    EditText idfEmailAddressEditText;
    Button advanceToSymptomsFromIdfButton;
    RadioGroup idfGenderRadioGroup;
    RadioButton selectedRadioButton;
    String age;

    PersonData user_data= new PersonData();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_identification_data_form);

        idfMobileNoEditText=(EditText) findViewById(R.id.idfMobileNoEditText);
        idfNameEditText = (EditText) findViewById(R.id.idfNameEditText);
        idfEmailAddressEditText = (EditText) findViewById(R.id.idfEmailAddressEditText);
        idfGenderRadioGroup = (RadioGroup) findViewById(R.id.idfGenderRadioGroup);

        final Button backToMainFromIdfButton = (Button) findViewById(R.id.backToMainFromIDFButton);
        backToMainFromIdfButton.setOnClickListener(this);

        final Calendar calendar = Calendar.getInstance();
        idfDobEditText = (EditText) findViewById(R.id.idfDobEditText);
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            age=getAge(year, month, dayOfMonth);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            idfDobEditText.setText(simpleDateFormat.format(calendar.getTime()));
        };
        idfDobEditText.setOnClickListener(v -> new DatePickerDialog(IdentificationDataFormActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show());

        advanceToSymptomsFromIdfButton = (Button) findViewById(R.id.advanceToSymptomsFromIDFButton);

        intent = new Intent(getApplicationContext(),SymptomsFormActivity.class);

        advanceToSymptomsFromIdfButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                user_data.setName(idfNameEditText.getText().toString());
                user_data.setAge(age);

                if(idfGenderRadioGroup.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext(), "Please select Gender", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // get selected radio button from radioGroup
                    int selectedId = idfGenderRadioGroup.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    selectedRadioButton = (RadioButton)findViewById(selectedId);
                }
                user_data.setSex(selectedRadioButton.getText().toString());
                user_data.setMobileNo(idfMobileNoEditText.getText().toString());
                user_data.setEmail(idfEmailAddressEditText.getText().toString());

                intent.putExtra("FormData",user_data);
                startActivity(intent);
//                Toast.makeText(getApplicationContext(),"Name:"+user_data.name+"\nAge:"+user_data.age+"\nSex:"+user_data.sex+"\nNo:"+user_data.mobileNo+"\nEmail:"+user_data.email,Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backToMainFromIDFButton) {
            Intent toMainIntent = new Intent(IdentificationDataFormActivity.this, MainActivity.class);
            startActivity(toMainIntent);
        } else if (v.getId() == R.id.advanceToSymptomsFromIDFButton) {
            Intent toSymptomsIntent = new Intent(IdentificationDataFormActivity.this, SymptomsFormActivity.class);
            startActivity(toSymptomsIntent);
        }
    }

}