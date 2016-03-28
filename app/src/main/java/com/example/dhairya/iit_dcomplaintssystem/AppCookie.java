package com.example.dhairya.complaintsystem;

import android.app.Application;

import java.net.CookieHandler;
import java.net.CookieManager;


public class AppCookie extends Application {
    CookieManager cookieManager;

    public void onCreate(){
        cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        super.onCreate();
    }

}
