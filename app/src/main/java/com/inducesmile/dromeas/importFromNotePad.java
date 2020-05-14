package com.inducesmile.dromeas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class importFromNotePad extends AppCompatActivity {

    EditText addcode;
    Button notepadRun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_from_note_pad);

        Intent intent=getIntent();
        final String lang=intent.getStringExtra("language");

        addcode=findViewById(R.id.addcode);
        notepadRun=findViewById(R.id.notepadRun);

        notepadRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(importFromNotePad.this,newCodeRunOutput.class);
                intent.putExtra("code",addcode.getText());
                intent.putExtra("language",lang);
                startActivity(intent);
            }
        });
    }
}
