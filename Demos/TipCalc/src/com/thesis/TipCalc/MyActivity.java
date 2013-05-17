package com.thesis.TipCalc;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyActivity extends Activity {

    EditText pretip;
    TextView result, result2;
    Button button10, button15, button20;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
        LinearLayout root = new LinearLayout(this);
        addContentView(root, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        root.setOrientation(LinearLayout.VERTICAL);

        pretip = new EditText(this);
        pretip.setHint("enter pre-tip amount here");
        pretip.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        root.addView(pretip);

        result = new TextView(this);
        result.setText("Choose a percentage to calculate");
        result.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        root.addView(result);

        result2 = new TextView(this);
        result2.setText("Total: 0.00");
        result2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        root.addView(result2);

        button10 = new Button(this);
        button10.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button10.setText("10%");
        button10.setOnClickListener(onClickListener);

        button15 = new Button(this);
        button15.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button15.setText("15%");
        button15.setOnClickListener(onClickListener);

        button20 = new Button(this);
        button20.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button20.setText("20%");
        button20.setOnClickListener(onClickListener);

        root.addView(button10);
        root.addView(button15);
        root.addView(button20);
    }

    private static double calculateTip (double amount, int percent) {
        return amount * (percent / 100.0);
    }

    final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
            public void onClick(View view) {
                double pretipAmt;
                try {
                    pretipAmt = Double.parseDouble(pretip.getText().toString());
                } catch (NumberFormatException e) {
                    pretipAmt = 0.0;
                }

                double tip = 0.0;
            if (view == button10)
                tip = calculateTip(pretipAmt, 10);
            else if (view == button15)
                tip = calculateTip(pretipAmt, 15);
            else if (view == button20)
                tip = calculateTip(pretipAmt, 20);

            result.setText(String.format("Tip: %.2f", tip));
            result2.setText(String.format("Total: %.2f", tip + pretipAmt));
        }
    };
}
