package com.gopal.weather.service;

import com.gopal.weather.data.Channel;

/**
 * Created by gopal on 26/9/15.
 */
public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
