package com.thesis;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup rootView = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);

        Button object1 = new Button(this);
        object1.setText("");
        object1.setHeight(100);
        object1.setWidth(100);
        rootView.addView(object1);

        Button Brian = new Button(this);
        Brian.setText("");
        Brian.setHeight(80);
        Brian.setWidth(100);
        Brian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        rootView.addView(Brian);

        TextView textview1 = new TextView(this);
        textview1.setText("Hello");
        textview1.setHeight(100);
        textview1.setWidth(100);
        rootView.addView(textview1);

    }
}