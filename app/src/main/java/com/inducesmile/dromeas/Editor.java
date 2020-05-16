package com.inducesmile.dromeas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Editor extends AppCompatActivity {

    String code;
    Button saveCode;
    EditText codeEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        codeEditor=findViewById(R.id.codeEditor);

        saveCode=findViewById(R.id.saveCode);

        Intent intent=getIntent();
        code=intent.getStringExtra("code");

        codeEditor.setText(code);

        saveCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code=codeEditor.getText().toString();
                Intent resultIntent = new Intent();


                // TODO Add extras or a data URI to this intent as appropriate.
                resultIntent.putExtra("accessedData", code);
                setResult(Editor.RESULT_OK, resultIntent);
                finish();
            }
        });


    }


}
