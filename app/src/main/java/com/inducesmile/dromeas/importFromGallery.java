package com.inducesmile.dromeas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

public class importFromGallery extends AppCompatActivity {


    ImageView imageView;
    Button openEditor,getimage,runCodeFromGallary;

    private static final int CAMERA_REQUEST_CODE=200;
    private static final int STORAGE_REQUEST_CODE=400;
    private static final int IMAGE_PICK_CAMERA_CODE=1001;
    private static final int IMAGE_PICK_GALLERY_CODE=1004;

    private static final  int imagePickCode = 1000;
    private static final  int permissionCode = 1001;

    Uri uri;

    String cameraPermission[];
    String storagePermission[];
    String code="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_from_gallery);

        imageView=findViewById(R.id.galleryimage);
        getimage=findViewById(R.id.getimage);
        openEditor=findViewById(R.id.openEditor);
        runCodeFromGallary=findViewById(R.id.runCodeFromGallary);

        runCodeFromGallary.setEnabled(false);

        Intent intent=getIntent();
        final String lang=intent.getStringExtra("language");


        cameraPermission=new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        getimage.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        //permission not granted
                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permission,permissionCode);
                        pickImageFromGallery();


                    }
                    else
                    {
                        //permission already granted
                        pickImageFromGallery();

                    }
                }
                else
                {
                    // os version not compatible
                    pickImageFromGallery();

                }
            }

        });

        openEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(importFromGallery.this,Editor.class);
                intent1.putExtra("code",code);
                startActivityForResult(intent1,3);
            }
        });

        runCodeFromGallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(importFromGallery.this,newCodeRunOutput.class);
                intent.putExtra("language",lang);
                intent.putExtra("code",code);
                startActivity(intent);
            }
        });


    }


    private void pickImageFromGallery()
    {

        //CropImage.startPickImageActivity(importFromGallery.this);

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, imagePickCode);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case permissionCode:{
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //permission granted
                    pickImageFromGallery();
                }
                else
                {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (resultCode == RESULT_OK && requestCode == imagePickCode) {
            imageView.setImageURI(data.getData());
            CropImage.startPickImageActivity(importFromGallery.this);

        }*/

        if(requestCode==imagePickCode && resultCode== Activity.RESULT_OK){
            Uri imageUri=CropImage.getPickImageResultUri(this,data);
            if(CropImage.isReadExternalStoragePermissionsRequired(this,imageUri)){
                uri=imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);

            }else
            {
                startCrop(imageUri);
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
                        runCodeFromGallary.setEnabled(true);

                    }

                    imageView.setImageURI(result.getUri());
                    Toast.makeText(this, "Image Updated", Toast.LENGTH_LONG ).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
            else if( requestCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error=result.getError();
                Toast.makeText(this,""+error, Toast.LENGTH_LONG).show();
            }
        }

        if(requestCode==3){
            if (resultCode == Editor.RESULT_OK) {
                // TODO Extract the data returned from the child Activity.
                String returnValue = data.getStringExtra("accessedData");
                code =returnValue;

                //codepreview.setText(returnValue);
            }

        }



    }

    private void startCrop(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_REQUEST_CODE);

    }


    private boolean checkCameraPermission() {
        boolean result= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        boolean result2= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result && result2;
    }

}
