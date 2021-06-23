package com.mitwpu.anaemiadetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.Window;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.util.Objects;

public class LoadingResultsActivity extends AppCompatActivity {
    Intent fromCaptureImagesIntent;
    PersonData user_data;
    StringBuffer leftEyeImagePath, rightEyeImagePath;
    StringBuffer leftEyeImageEncoded, rightEyeImageEncoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_loading_results);

        fromCaptureImagesIntent = getIntent();
        user_data = (PersonData) fromCaptureImagesIntent.getSerializableExtra("FormData");
        leftEyeImagePath = user_data.leftEyeImagePath;
        rightEyeImagePath = user_data.rightEyeImagePath;

        Toast.makeText(getApplicationContext(), "Left:"+leftEyeImagePath.toString()+"\nRight"+rightEyeImagePath.toString(), Toast.LENGTH_LONG).show();
        Bitmap imageBitmap = BitmapFactory.decodeFile(rightEyeImagePath.toString());
        rightEyeImageEncoded=new StringBuffer(getBase64Image(imageBitmap));
        imageBitmap = BitmapFactory.decodeFile(leftEyeImagePath.toString());
        leftEyeImageEncoded=new StringBuffer(getBase64Image(imageBitmap));

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

    public String getBase64Image(Bitmap bitmap) {
        try {
            ByteBuffer buffer =
                    ByteBuffer.allocate(bitmap.getRowBytes() *
                            bitmap.getHeight());
            bitmap.copyPixelsToBuffer(buffer);
            byte[] data = buffer.array();
            return Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}