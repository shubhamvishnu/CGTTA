package com.cgtta.cgtta.classes;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by shubh on 5/9/2017.
 */

public class CGTTA extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}