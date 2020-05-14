package com.inducesmile.dromeas;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CommonLanding extends AppCompatActivity {

    Button notepad, camera, gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_landing);

        Intent intent=getIntent();
        final String lang=intent.getStringExtra("language");


        notepad= findViewById(R.id.notepad);
        camera= findViewById(R.id.camera);
        gallery= findViewById(R.id.gallery);

        notepad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommonLanding.this,importFromNotePad.class);
                intent.putExtra("language",lang);
                startActivity(intent);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommonLanding.this,newCameraOption.class);
                intent.putExtra("language",lang);
                startActivity(intent);
            }
        });


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommonLanding.this,importFromGallery.class);
                intent.putExtra("language",lang);
                startActivity(intent);
            }
        });
    }
}
