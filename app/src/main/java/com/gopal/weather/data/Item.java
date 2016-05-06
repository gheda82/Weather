package com.gopal.weather.data;

import org.json.JSONObject;

/**
 * Created by gopal on 26/9/15.
 */
public class Item implements JSONopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void poupulate(JSONObject data) {
        condition=new Condition();
        condition.poupulate(data.optJSONObject("condition"));
    }
}
