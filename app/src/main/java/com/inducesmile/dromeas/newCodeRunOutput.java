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

import org.json.*;

import java.io.IOException;

public class newCodeRunOutput extends AppCompatActivity {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    OkHttpClient client = new OkHttpClient();

    Button postCode;

    String changeURL="a5cdce3d.ngrok.io";

    TextView result;

    String postRequestURL="";

    String code="";
    //String sxx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_code_run_output);

        //postCode=findViewById(R.id.postCode);
        result=findViewById(R.id.result);

        Intent intent=getIntent();
        String num=intent.getStringExtra("language");
        code=intent.getStringExtra("code");
       // JsonConverter sa=new JsonConverter(code);
       // JSONObject sx= sa.convert();
      //  sxx= sx.toString();


        switch (num){
            case "1":
                postRequestURL="http://"+changeURL+"/api/run/c";
                runCode();
                break;

            case "2":
                postRequestURL="http://"+changeURL+"/api/run/c++";
                runCode();
                break;

            case "3":
                postRequestURL="http://"+changeURL+"/api/run/js";
                runCode();
                break;
        }



    }

    private void runCode() {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    JSONObject obj = new JSONObject();
                    obj.put("code", code);

                    String json=obj.toString();

                    RequestBody body = RequestBody.create(JSON, json); // new
                    Request request = new Request.Builder()
                            .url(postRequestURL)
                            .post(body)
                            .build();
                    Response response = client.newCall(request).execute();
                    String str = response.body().string();
                    result.setText(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}

/*
class JsonConverter {
    private String code;
    JsonConverter(String code) { this.code = code;}

    JSONObject convert() {
        String jsonString = "{\"code\" : \"";
        for(int i = 0; i < code.length(); i++){
            switch(code.charAt(i)){

                case '\t': jsonString += "\\\\t";
                    break;

                case '\b': jsonString += "\\\\b";
                    break;

                case '\n': jsonString += "\\\\n";
                    break;

                case '\r': jsonString += "\\\\r";
                    break;

                case '\f': jsonString += "\\\\f";
                    break;

                case '\'': jsonString += "\\\\\\'";
                    break;

                case '"': jsonString += "\\\\\\\"";
                    break;

                case '\\': jsonString += "\\\\";
                    break;

                default: jsonString += code.charAt(i);
            }
        }

        jsonString += "\"}";
        System.out.println(jsonString);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        }
        catch ( JSONException je){
            System.out.println(je);
        }
        return jsonObject;
    }

    //Remove this function

}*/
