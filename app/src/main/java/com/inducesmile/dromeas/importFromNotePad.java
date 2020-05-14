package com.inducesmile.dromeas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class importFromNotePad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_from_note_pad);

        Intent intent=getIntent();
        final String lang=intent.getStringExtra("language");
    }
}
