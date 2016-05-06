package com.gopal.weather.data;

import org.json.JSONObject;

/**
 * Created by gopal on 26/9/15.
 */
public class Units implements JSONopulator {
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void poupulate(JSONObject data) {
        temperature=data.optString("temperature");
    }
}
