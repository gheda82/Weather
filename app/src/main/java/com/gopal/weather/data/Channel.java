package com.gopal.weather.data;
import android.support.v4.app.FragmentActivity;

import com.gopal.weather.data.JSONopulator;

import org.json.JSONObject;

/**
 * Created by gopal on 26/9/15.
 */
public class Channel implements JSONopulator {
    private Item item;
    private Units units;

    public Item getItem() {
        return item;
    }

    public Units getUnits() {
        return units;
    }

    @Override
    public void poupulate(JSONObject data) {
        units=new Units();
        units.poupulate(data.optJSONObject("units"));

        item=new Item();
        item.poupulate(data.optJSONObject("item"));
    }
}
