package com.inducesmile.dromeas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class ViewResultForCamera extends AppCompatActivity {

    ImageView mPreview;

    TextView codePreview;

    String code="";

    Button openTextEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result_for_camera);

        mPreview=findViewById(R.id.mPreview);
        codePreview=findViewById(R.id.codePreview);
        openTextEditor=findViewById(R.id.openTextEditor);

        final Intent intent=getIntent();
        String image= intent.getStringExtra("uri");
        Uri fileUri = Uri.parse(image);
        mPreview.setImageURI(fileUri);

        BitmapDrawable bitmapDrawable=(BitmapDrawable)mPreview.getDrawable();
        Bitmap bitmap=bitmapDrawable.getBitmap();

        TextRecognizer recognizer=new TextRecognizer.Builder(getApplicationContext()).build();

        if (!recognizer.isOperational()){
            Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
        }
        else {
            Frame frame=new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> items=recognizer.detect(frame);
            StringBuilder sb= new StringBuilder();

            for(int i=0;i<items.size();i++){
                TextBlock myItem=items.valueAt(i);
                sb.append(myItem.getValue());
                sb.append("\n");
            }


            codePreview.setText(sb.toString());
            code=sb.toString();

        }

        openTextEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(ViewResultForCamera.this,Editor.class);
                intent2.putExtra("Code",code);
                startActivity(intent2);
            }
        });

    }
}
