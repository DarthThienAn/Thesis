package com.thesis.Accel;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;


    private LinearLayout root;
    private float x, y, z;
    private TextView tvX, tvY, tvZ, direction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        root = new LinearLayout(this);
        addContentView(root, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        root.setOrientation(LinearLayout.VERTICAL);

        TextView text = new TextView(this);
        text.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        text.setText("Shake!");
        root.addView(text);

        tvX = new TextView(this);
        tvX.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tvY = new TextView(this);
        tvY.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tvZ = new TextView(this);
        tvZ.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        direction = new TextView(this);
        direction.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        root.addView(tvX);
        root.addView(tvY);
        root.addView(tvZ);
        root.addView(direction);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float newX = event.values[0];
        float newY = event.values[1];
        float newZ = event.values[2];
        float deltaX = Math.abs(x - newX);
        float deltaY = Math.abs(y - newY);
        float deltaZ = Math.abs(z - newZ);
        if (deltaX < 2.0) deltaX = (float)0.0;
        if (deltaY < 2.0) deltaY = (float)0.0;
        if (deltaZ < 2.0) deltaZ = (float)0.0;
        x = newX;
        y = newY;
        z = newZ;
        tvX.setText(Float.toString(deltaX));
        tvY.setText(Float.toString(deltaY));
        tvZ.setText(Float.toString(deltaZ));
        direction.setVisibility(View.VISIBLE);
        if (deltaX > deltaY) {
            direction.setText("horizontal");
        } else if (deltaY > deltaX) {
            direction.setText("vertical");
        } else {
            direction.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
