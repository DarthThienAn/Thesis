package com.thesis;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

public class NActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup rootView = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.addView(createNewButton());
    }


    public Button createNewButton() {
        Button button = new Button(this);
        button.setText("Button");
//        button.setLayoutParams(new FrameLayout.LayoutParams(360, 480));
        button.setHeight(100);
        button.setWidth(100);
        return button;
    }
}
