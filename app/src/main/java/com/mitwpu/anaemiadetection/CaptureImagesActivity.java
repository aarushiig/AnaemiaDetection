package com.mitwpu.anaemiadetection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import dalvik.system.InMemoryDexClassLoader;

public class CaptureImagesActivity extends AppCompatActivity implements View.OnClickListener {
    Intent fromSymptomsIntent;
    PersonData user_data;
    ImageView captureImagesRightEyeImage, captureImagesLeftEyeImage;
    Button captureImagesClickRightEyeImageButton, captureImagesClickLeftEyeImageButton;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    static final int REQUEST_RIGHT_CAPTURE = 1;
    static final int REQUEST_LEFT_CAPTURE = 2;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    String currentImagePath = null;
    String leftImagePath = null;
    String rightImagePath = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_capture_images);

        fromSymptomsIntent = getIntent();
        user_data= (PersonData) fromSymptomsIntent.getSerializableExtra("FormData");

        captureImagesRightEyeImage = (ImageView) this.findViewById(R.id.captureImagesRightEyeImage);
        captureImagesLeftEyeImage = (ImageView) this.findViewById(R.id.captureImagesLeftEyeImage);
        captureImagesClickRightEyeImageButton = (Button) this.findViewById(R.id.captureImagesClickRightEyeImageButton);
        captureImagesClickLeftEyeImageButton = (Button) this.findViewById(R.id.captureImagesClickLeftEyeImageButton);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (getFromPref(this, ALLOW_KEY)) {
                showSettingsAlert();
            } else if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)

                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {
                    showAlert();
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }
        } else {
            //openCamera();
        }

        final Button backToSymptomsFromCaptureImages = (Button) findViewById(R.id.backToSymptomsFromCaptureImagesButton);
        backToSymptomsFromCaptureImages.setOnClickListener(this);

        captureImagesClickLeftEyeImageButton.setOnClickListener(this);
        captureImagesClickRightEyeImageButton.setOnClickListener(this);

        final Button getResultsButton = (Button) findViewById(R.id.getResultsButton);
        getResultsButton.setOnClickListener(this);
    }

    public static void saveToPreferences(Context context, String key, Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences(CAMERA_PREF,
                Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(CaptureImagesActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(CaptureImagesActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                });
        alertDialog.show();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(CaptureImagesActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInstalledAppDetailsActivity(CaptureImagesActivity.this);
                    }
                });

        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];

                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean
                                showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale(
                                        this, permission);

                        if (showRationale) {
                            showAlert();
                        } else if (!showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            saveToPreferences(CaptureImagesActivity.this, ALLOW_KEY, true);
                        }
                    }
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }

        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    private void openCamera(int camera){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File imageFile = null;
            try {
                imageFile = getImageFile(camera);
            } catch (IOException ex) {
                // Error occurred while creating the File
                System.out.println("Error occurred while creating the File");
            }
            // Continue only if the File was successfully created
            if (imageFile != null) {
                Uri imageURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
//                startActivityForResult(cameraIntent, REQUEST_RIGHT_CAPTURE);
                if(camera==1)
                    startActivityForResult(cameraIntent, REQUEST_RIGHT_CAPTURE);
                else
                    startActivityForResult(cameraIntent, REQUEST_LEFT_CAPTURE);
            }
        }
        else{
            System.out.println("cameraIntent.resolveActivity(getPackageManager()) != null failed");
        }

//        File imageFile = null;
//        try {
//            imageFile = getImageFile(camera);
//        } catch (IOException ex) {
//            // Error occurred while creating the File
//            System.out.println("Error occurred while creating the File");
//        }
//        // Continue only if the File was successfully created
//        if (imageFile != null) {
//            Uri imageURI = FileProvider.getUriForFile(this,
//                    "com.example.android.fileprovider",
//                    imageFile);
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
//            if(camera==1)
//                startActivityForResult(cameraIntent, REQUEST_RIGHT_CAPTURE);
//            else
//                startActivityForResult(cameraIntent, REQUEST_LEFT_CAPTURE);
//        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RIGHT_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(rightImagePath);
            captureImagesRightEyeImage.setImageBitmap(imageBitmap);
            captureImagesClickRightEyeImageButton.setText("Retake Picture of Right Eye");
        }
        if (requestCode == REQUEST_LEFT_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(leftImagePath);
            captureImagesLeftEyeImage.setImageBitmap(imageBitmap);
            captureImagesClickLeftEyeImageButton.setText("Retake Picture of Left Eye");
        }
    }

    private File getImageFile(int camera) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentImagePath = imageFile.getAbsolutePath();
        if(camera==1)
            rightImagePath = imageFile.getAbsolutePath();
        else
            leftImagePath = imageFile.getAbsolutePath();
        return imageFile;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backToSymptomsFromCaptureImagesButton) {
            Intent toSymptomsIntent = new Intent(CaptureImagesActivity.this, SymptomsFormActivity.class);
            startActivity(toSymptomsIntent);
        }
        else if (v.getId() == R.id.captureImagesClickLeftEyeImageButton) {
            openCamera(0);
        } else if (v.getId() == R.id.captureImagesClickRightEyeImageButton) {
            openCamera(1);
        }
        else if (v.getId() == R.id.getResultsButton) {
            Intent toResultsIntent = new Intent(CaptureImagesActivity.this, ResultsActivity.class);
            startActivity(toResultsIntent);
        }
    }


}