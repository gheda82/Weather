package com.gopal.weather.data;

import org.json.JSONObject;

/**
 * Created by gopal on 26/9/15.
 */
public class Condition implements JSONopulator {

    private int code;
    private int temperature;
    private String description;


    public int getCode() {
        return code;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void poupulate(JSONObject data) {
        code=data.optInt("code");
        temperature=data.optInt("temp");
        description=data.optString("text");
    }
}
