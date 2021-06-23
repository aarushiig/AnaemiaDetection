package com.mitwpu.anaemiadetection;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import java.util.regex.Pattern;

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

        DatePickerDialog datePickerDialog = new DatePickerDialog(IdentificationDataFormActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        idfDobEditText.setOnClickListener(v -> datePickerDialog.show());

        advanceToSymptomsFromIdfButton = (Button) findViewById(R.id.advanceToSymptomsFromIDFButton);
        advanceToSymptomsFromIdfButton.setOnClickListener(this);
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

    public boolean isETEmpty(EditText et){
        return (et != null && (et.getText().toString().equals("") || et.getText().toString().equals(" ")));//the final piece checks if the edittext is empty or just contains a space. It is or between
        //in order to ensure that one of those are true. Thus the parenthesis
    }

    private boolean isValidEmail(String email) {

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        return Pattern.compile(EMAIL_STRING).matcher(email).matches();

    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("[7-9][0-9]{9}");
    }

//    public static boolean checkEditTextIsEmpty(EditText... editTexts)
//    {
//        try
//        {
//            for (EditText editText : editTexts)
//            {
//                if (editText.getText().toString().trim().length() == 0)
//                {
//                    Drawable d = _application.currentActivity.getResources().getDrawable(android.R.drawable.ic_dialog_alert);
//                    d.setBounds(0, 0, d.getIntrinsicWidth()/2, d.getIntrinsicHeight()/2);
//                    editText.requestFocus();
//                    editText.setError(editText.getHint() + " is empty", d);
//                    return false;
//                }
//            }
//        }
//        catch (Exception ignored)
//        {
//            return false;
//        }
//        return true;
//    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backToMainFromIDFButton) {
            Intent toMainIntent = new Intent(IdentificationDataFormActivity.this, MainActivity.class);
            startActivity(toMainIntent);
        } else if (v.getId() == R.id.advanceToSymptomsFromIDFButton) {
            Intent toSymptomsIntent = new Intent(IdentificationDataFormActivity.this, SymptomsFormActivity.class);

            if(isETEmpty(idfNameEditText) || isETEmpty(idfEmailAddressEditText) || isETEmpty(idfMobileNoEditText) || isETEmpty(idfDobEditText) || idfGenderRadioGroup.getCheckedRadioButtonId()==-1){
                Toast.makeText(getApplicationContext(), "Please fill all the data", Toast.LENGTH_SHORT).show();

//                if(idfGenderRadioGroup.getCheckedRadioButtonId()==-1)
//                {
//                    Toast.makeText(getApplicationContext(), "Please select Gender", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(), "Please fill all the data", Toast.LENGTH_SHORT).show();
//                }

            }
            else if(!isValidPhoneNumber(idfMobileNoEditText.getText().toString())){
                Toast.makeText(getApplicationContext(), "Invalid Mobile Number!", Toast.LENGTH_SHORT).show();
            }
            else if(!isValidEmail(idfEmailAddressEditText.getText().toString())){
                Toast.makeText(getApplicationContext(), "Invalid Email!", Toast.LENGTH_SHORT).show();
            }
            else{
                user_data.setName(idfNameEditText.getText().toString());
                user_data.setAge(age);
                user_data.setMobileNo(idfMobileNoEditText.getText().toString());
                user_data.setEmail(idfEmailAddressEditText.getText().toString());

                int selectedId = idfGenderRadioGroup.getCheckedRadioButtonId();
                selectedRadioButton = (RadioButton)findViewById(selectedId);
                user_data.setSex(selectedRadioButton.getText().toString());

                toSymptomsIntent.putExtra("FormData",user_data);
                startActivity(toSymptomsIntent);
            }

        }
    }

}