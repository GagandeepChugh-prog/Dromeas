package com.inducesmile.dromeas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class openCameraActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE=200;
    private static final int STORAGE_REQUEST_CODE=400;
    private static final int IMAGE_PICK_CAMERA_CODE=1001;
    private static final int IMAGE_PICK_GALLERY_CODE=1001;

    String cameraPermission[];
    String storagePermission[];

    Uri image_uri;

    String code="";

    String beforeCode="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_camera);

        Intent intent=getIntent();
        beforeCode=intent.getStringExtra("code");

        cameraPermission=new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if(!checkCameraPermission()){
            requestCameraPermission();
        }
        else{
            pickCamera();
        }


    }
    private void pickCamera() {
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"NewPic");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image To Text");
        image_uri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraIntent,IMAGE_PICK_CAMERA_CODE);
        //finish();
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_REQUEST_CODE);

    }

    private boolean checkCameraPermission() {
        boolean result= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result2= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result && result2;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //  super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                if(grantResults.length>0){
                    boolean cameraAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && writeStorageAccepted){
                        pickCamera();
                    }
                    else {
                        Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }

        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //   super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                CropImage.activity(image_uri).setGuidelines(CropImageView.Guidelines.ON).start(this);
            }
        }

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
            //    Uri resultUri=;

                Uri fileUri = result.getUri();
               // mPreview.setImageURI(fileUri);

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);

                    TextRecognizer recognizer=new TextRecognizer.Builder(getApplicationContext()).build();

                    if (!recognizer.isOperational()){
                        Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Frame frame=new Frame.Builder().setBitmap(bitmap).build();
                        SparseArray<TextBlock> items=recognizer.detect(frame);
                        StringBuilder sb= new StringBuilder();

                        for(int i=0;i<items.size();i++){
                            TextBlock myItem=items.valueAt(i);
                            sb.append(myItem.getValue());
                            sb.append("\n");
                        }


                      //  codePreview.setText(sb.toString());
                        code=sb.toString();
                        fireBackActivity();

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
            else if( requestCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error=result.getError();
                Toast.makeText(this,""+error, Toast.LENGTH_LONG).show();
            }
        }

        if (resultCode == 0){
            fireBackActivity();
        }

    }

    private void fireBackActivity() {
        //Intent intent=new Intent(openCameraActivity.this,);
        code=beforeCode+code;

        Intent resultIntent = new Intent();

        // TODO Add extras or a data URI to this intent as appropriate.
        resultIntent.putExtra("accessedData", code);
        setResult(openCameraActivity.RESULT_OK, resultIntent);
        finish();
    }

}
