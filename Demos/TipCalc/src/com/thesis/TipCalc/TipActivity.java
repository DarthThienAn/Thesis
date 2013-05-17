package com.thesis.TipCalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TipActivity extends Activity {


    EditText pretip;
    TextView tip, total;
    Button button10, button15, button20;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        pretip = (EditText) findViewById(R.id.pretip);
        tip = (TextView) findViewById(R.id.tip);
        total = (TextView) findViewById(R.id.total);
        button10 = (Button) findViewById(R.id.button10);
        button15 = (Button) findViewById(R.id.button15);
        button20 = (Button) findViewById(R.id.button20);
        button10.setOnClickListener(onClickListener);
        button15.setOnClickListener(onClickListener);
        button20.setOnClickListener(onClickListener);
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

            TipActivity.this.tip.setText(String.format("Tip: %.2f", tip));
            total.setText(String.format("Total: %.2f", tip + pretipAmt));
        }
    };
}
