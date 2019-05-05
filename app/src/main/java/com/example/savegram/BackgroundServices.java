package com.example.savegram;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

@SuppressLint("Registered")
public class BackgroundServices extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // do your jobs here
        return super.onStartCommand(intent, flags, startId);
    }
}

// reference
/// https://stackoverflow.com/questions/34573109/how-to-make-an-android-app-to-always-run-in-background