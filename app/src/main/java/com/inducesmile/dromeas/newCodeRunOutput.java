package com.inducesmile.dromeas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class newCodeRunOutput extends AppCompatActivity {

    Button postCode;

    String changeURL="d756f727.ngrok.io";

    TextView result;

    String postRequestURL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_code_run_output);

        postCode=findViewById(R.id.postCode);
        result=findViewById(R.id.result);

        Intent intent=getIntent();
        String num=intent.getStringExtra("language");
        String code=intent.getStringExtra("code");

        switch (num){
            case "1":
                postRequestURL="http://"+changeURL+"/api/run/c";
                break;

            case "2":
                postRequestURL="http://"+changeURL+"/api/run/c++";
                break;

            case "3":
                postRequestURL="http://"+changeURL+"/api/run/js";
                break;
        }


        postCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {
                            OkHttpClient client = new OkHttpClient();
                            MediaType mediaType = MediaType.parse("application/json");
                            RequestBody body = RequestBody.create(mediaType, "{\n\t\"code\" : \"#include <stdio.h>\\nint main()\\n{\\nint a = 10, b = 20;\\nint c = a + b;\\nprintf(\\\"%d\\\",c);\\nreturn -1;}\"\n}");
                            Request request = new Request.Builder()
                                    .url(postRequestURL)
                                    .method("POST", body)
                                    .addHeader("Content-Type", "application/json")
                                    .build();
                            try {
                                Response response = client.newCall(request).execute();
                                String answer=response.body().string();
                                result.setText(answer);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
        });
    }
}

