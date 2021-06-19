package com.mitwpu.anaemiadetection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Objects;

import dalvik.system.InMemoryDexClassLoader;

public class CaptureImagesActivity extends AppCompatActivity implements View.OnClickListener {

//    ImageView leftEyeImage = (ImageView) findViewById(R.id.captureImagesLeftEyeImage);
//    ImageView rightEyeImage = (ImageView) findViewById(R.id.captureImagesRightEyeImage);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_capture_images);

        final Button backToSymptomsFromCaptureImages = (Button) findViewById(R.id.backToSymptomsFromCaptureImagesButton);
        backToSymptomsFromCaptureImages.setOnClickListener(this);

//        final Button clickLeftEyeImageButton = (Button) findViewById(R.id.captureImagesClickLeftEyeImageButton);
//        clickLeftEyeImageButton.setOnClickListener(this);

//        final Button clickRightEyeImageButton = (Button) findViewById(R.id.captureImagesClickRightEyeImageButton);
//        clickRightEyeImageButton.setOnClickListener(this);

        final Button getResultsButton = (Button) findViewById(R.id.getResultsButton);
        getResultsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backToSymptomsFromCaptureImagesButton) {
            Intent toSymptomsIntent = new Intent(CaptureImagesActivity.this, SymptomsFormActivity.class);
            startActivity(toSymptomsIntent);
        }
//        else if (v.getId() == R.id.captureImagesClickLeftEyeImageButton) {
//            Intent leftEyeImageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(leftEyeImageCaptureIntent, 0);
//        } else if (v.getId() == R.id.captureImagesClickRightEyeImageButton) {
//            Intent rightEyeImageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(rightEyeImageCaptureIntent, 1);
//        }
        else if (v.getId() == R.id.getResultsButton) {
            Intent toLoadingResultsIntent = new Intent(CaptureImagesActivity.this, LoadingResultsActivity.class);
            startActivity(toLoadingResultsIntent);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 0) {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            leftEyeImage.setImageBitmap(bitmap);
//        } else if (requestCode == 1) {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            rightEyeImage.setImageBitmap(bitmap);
//        }
//    }
}