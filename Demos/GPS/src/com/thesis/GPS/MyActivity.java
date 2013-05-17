package com.thesis.GPS;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyActivity extends Activity {

    double latitude, longitude;
    double latitude1, longitude1;
    double latitude2, longitude2;
    TextView textView, textView2, textView3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location last = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        latitude = last.getLatitude();
        longitude = last.getLongitude();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        });

        LinearLayout root = new LinearLayout(this);
        addContentView(root, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        root.setOrientation(LinearLayout.VERTICAL);

        Button button = new Button(this);
        button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button.setText("Record1");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location last = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                latitude = last.getLatitude();
                longitude = last.getLongitude();
                latitude1 = latitude;
                longitude1 = longitude;
                textView.setText(String.format("Lat: %.2f, Long: %.2f", latitude1, longitude1));
            }
        });
        root.addView(button);

        textView = new TextView(this);
        textView.setText("Lat: 0.0, Long: 0.0");
        root.addView(textView);

        Button button2 = new Button(this);
        button2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button2.setText("Record2");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location last = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                latitude = last.getLatitude();
                longitude = last.getLongitude();
                latitude2 = latitude;
                longitude2 = longitude;
                textView2.setText(String.format("Lat: %.2f, Long: %.2f", latitude2, longitude2));
            }
        });
        root.addView(button2);

        textView2 = new TextView(this);
        textView2.setText("Lat: 0.0, Long: 0.0");
        root.addView(textView2);

        Button button3 = new Button(this);
        button3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        button3.setText("Update");
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location loc1 = new Location(LocationManager.GPS_PROVIDER);
                loc1.setLatitude(latitude1);
                loc1.setLongitude(longitude1);

                Location loc2 = new Location(LocationManager.GPS_PROVIDER);
                loc2.setLatitude(latitude2);
                loc2.setLongitude(longitude2);

                textView3.setText(String.format("Distance: %.2f meters", loc1.distanceTo(loc2)));
            }
        });
        root.addView(button3);

        textView3 = new TextView(this);
        textView3.setText("Distance: 0.0");
        root.addView(textView3);

    }
}
