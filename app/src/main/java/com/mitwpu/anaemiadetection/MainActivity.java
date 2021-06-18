package com.mitwpu.anaemiadetection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        final Button aboutButton = (Button) findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(this);
        final Button getStartedButton = (Button) findViewById(R.id.getStartedButton);
        getStartedButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.aboutButton) {
            Intent toAboutIntent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(toAboutIntent);
        } else if (v.getId() == R.id.getStartedButton) {
            Intent toIdentificationDataFormIntent = new Intent(MainActivity.this, IdentificationDataFormActivity.class);
            startActivity(toIdentificationDataFormIntent);
        }
    }

}