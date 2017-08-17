package com.example.observer;

/**
 * Created by James on 2016/11/2.
 */

//public class User implements Observer {
//    public Observable mObservable;
//    public String mCity = "";
//    public String mWeather = "";
//
//    public User(Observable observable) {
//        this.mObservable = observable;
//        mObservable.addObserver(this);
//    }
//
//    @Override
//    public void update(Observable o, Object arg) {
//        if (o instanceof WeatherForecast) {
//            mCity = ((WeatherForecast) o).getCity();
//            mWeather = ((WeatherForecast) o).getWeather();
//            display();
//        }
//        if (arg instanceof Pair) {
//            display();
//        }
//    }
//
//    public void display() {
//        System.out.println(mCity + "天气" + mWeather);
//    }
//}
