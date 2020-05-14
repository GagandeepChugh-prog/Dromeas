package com.inducesmile.dromeas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class importFromGallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_from_gallery);

        Intent intent=getIntent();
        final String lang=intent.getStringExtra("language");
    }
}
