package com.inducesmile.dromeas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class Editor extends AppCompatActivity {

    String code;
    EditText codeEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        codeEditor=findViewById(R.id.codeEditor);

        Intent intent=getIntent();
        code=intent.getStringExtra("code");

        codeEditor.setText(code);


    }
}
