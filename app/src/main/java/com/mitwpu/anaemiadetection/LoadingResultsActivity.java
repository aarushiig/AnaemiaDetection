package com.mitwpu.anaemiadetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import java.util.Objects;

public class LoadingResultsActivity extends AppCompatActivity {
    Intent fromCaptureImagesIntent;
    PersonData user_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_loading_results);

        fromCaptureImagesIntent = getIntent();
        user_data = (PersonData) fromCaptureImagesIntent.getSerializableExtra("FormData");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent toResultsIntent = new Intent(LoadingResultsActivity.this, ResultsActivity.class);
                user_data.setResult("Not Set");
                user_data.setPercentage("50");
                toResultsIntent.putExtra("FormData",user_data);
                startActivity(toResultsIntent);
                finish();
            }
        }, 3000);
    }
}