package com.inducesmile.dromeas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CodeRunOutput extends AppCompatActivity {

    private String file="myFile";
    private String fileContents;

    private Button readText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_run_output);

        readText=findViewById(R.id.readText);
        textView=findViewById(R.id.textView5);

        Intent intent=getIntent();
        fileContents=intent.getStringExtra("Code");

        try{
            FileOutputStream fOut=openFileOutput(file,MODE_PRIVATE);
            fOut.write(fileContents.getBytes());
            fOut.close();
            File fileDir=new File(getFilesDir(),file);
            Toast.makeText(getBaseContext(),"File Saved At = "+fileDir,Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        readText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    FileInputStream fIn=openFileInput(file);
                    int c;
                    String temp="";
                    while((c=fIn.read())!=-1){
                        temp=temp + Character.toString((char)c);
                    }
                    textView.setText(temp);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });




    }
}
