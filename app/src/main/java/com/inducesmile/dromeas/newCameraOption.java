package com.inducesmile.dromeas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class newCameraOption extends AppCompatActivity {

    Button openCamera,runCode,viewCode;
    TextView codepreview;

    String code="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_camera_option);

        Intent intent=getIntent();
        final String lang=intent.getStringExtra("language");

        openCamera=findViewById(R.id.openCamera);
        runCode=findViewById(R.id.runCode);
        viewCode=findViewById(R.id.viewCode);
        codepreview=findViewById(R.id.codepreview);

        viewCode.setEnabled(false);
        runCode.setEnabled(false);

        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(newCameraOption.this,openCameraActivity.class);
                intent.putExtra("code",code);
                startActivityForResult(intent,2);
            }
        });

        runCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(newCameraOption.this,newCodeRunOutput.class);
                intent.putExtra("language",lang);
                intent.putExtra("code",code);
                startActivity(intent);
            }
        });


        viewCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(newCameraOption.this,Editor.class);
                intent.putExtra("code",code);
                startActivityForResult(intent,3);
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (2) : {
                if (resultCode == openCameraActivity.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
                    String returnValue = data.getStringExtra("accessedData");
                    String cx=returnValue;

                    if(cx.length()>0){
                        openCamera.setText("Add More Photos");
                        code=code+cx;
                        viewCode.setEnabled(true);
                        runCode.setEnabled(true);

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"No code detected",Toast.LENGTH_LONG).show();
                    }
                 //   codepreview.setText(returnValue);
                }
                break;
            }

            case 3:{
                if (resultCode == Editor.RESULT_OK) {
                    // TODO Extract the data returned from the child Activity.
                    String returnValue = data.getStringExtra("accessedData");
                    code =returnValue;
                //    codepreview.setText(returnValue);
                }
                break;
            }

        }
    }
}
