package com.gopal.weather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.gopal.weather.data.Channel;
import com.gopal.weather.data.Item;
import com.gopal.weather.service.WeatherServiceCallback;
import com.gopal.weather.service.YahooWeatherService;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by gopal on 26/9/15.
 */
public class MainActivity extends ActionBarActivity implements WeatherServiceCallback {

    GPSTracker gps;
    private ImageView WeatherIcon;
    private TextView TemperatureTextView;
    private TextView ConditionTextView;
    private TextView LocationTextView;

    private YahooWeatherService service;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeatherIcon = (ImageView) findViewById(R.id.WeatherIcon);
        TemperatureTextView = (TextView) findViewById(R.id.Temperature);
        ConditionTextView = (TextView) findViewById(R.id.Condition);
        LocationTextView = (TextView) findViewById(R.id.Location);
        service = new YahooWeatherService(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        gps = new GPSTracker(MainActivity.this);
        if (gps.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER )&& gps.locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            if (gps.canGetLocation()) {
                String loc = String.valueOf((Location) gps.location);
           /* double latitude=gps.getLatitude();
            double longitude=gps.getLongitude();
            String loc= String.valueOf(latitude+longitude);*/
                service.refreshWeather(loc);
            }
        }
        else
        {
            gps.showSettingsAlert();
        }
    }
    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());    //code for printing the human readable address
        StringBuilder builder = new StringBuilder();
        String finalAddress = null;
        try {
            List<Address> address = geocoder.getFromLocation(gps.latitude, gps.longitude, 1);
            int maxLines = address.get(0).getMaxAddressLineIndex();
            for (int i = 0; i < maxLines; i++) {
                String addressStr = address.get(0).getAddressLine(i);
                builder.append(addressStr);
                builder.append(" ");
            }

            finalAddress = builder.toString();				//ends here

            //  latituteField.setText(String.valueOf(lat));
            // longitudeField.setText(String.valueOf(lng));
            //addressField.setText(fnialAddress); //This will display the final address.

        } catch (IOException e) {
        } catch (NullPointerException e) {
        }
        Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/a" + item.getCondition().getCode(), null, getPackageName());
        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);
        WeatherIcon.setImageDrawable(weatherIconDrawable);

        //  TemperatureTextView.setText(item.getCondition().getTemperature()) + "\u00B0" +channel.getUnits().getTemperature());
        TemperatureTextView.setText(item.getCondition().getTemperature() + "\u00B0" + channel.getUnits().getTemperature());
        ConditionTextView.setText(item.getCondition().getDescription());
        LocationTextView.setText(finalAddress);

    }


    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this,exception.getMessage(),Toast.LENGTH_LONG).show();
    }
}
