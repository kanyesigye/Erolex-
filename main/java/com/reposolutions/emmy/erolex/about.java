package com.reposolutions.emmy.erolex;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class about extends AppCompatActivity {
    TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        tx=(TextView)findViewById(R.id.textView2);



        Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/Lato-Light.ttf");
        tx.setTypeface(custom_font);

    }

    public void back(View v){
        startActivity(new Intent(about.this,login.class));
    }
}
