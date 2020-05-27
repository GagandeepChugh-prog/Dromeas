package com.inducesmile.dromeas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

    public class LanguageOption extends AppCompatActivity {

    Button b1,b2,b3,b4,b5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_option);

        b1= findViewById(R.id.bt1);
        b2= findViewById(R.id.bt2);
        b3= findViewById(R.id.bt3);
        b4= findViewById(R.id.bt4);
        b5= findViewById(R.id.bt5);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LanguageOption.this,CommonLanding.class);
                intent.putExtra("language","1");
                startActivity(intent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LanguageOption.this,CommonLanding.class);
                intent.putExtra("language","2");
                startActivity(intent);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LanguageOption.this,CommonLanding.class);
                intent.putExtra("language","3");
                startActivity(intent);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LanguageOption.this,CommonLanding.class);
                intent.putExtra("language","4");
                startActivity(intent);
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LanguageOption.this,CommonLanding.class);
                intent.putExtra("language","5");
                startActivity(intent);
            }
        });
    }
}