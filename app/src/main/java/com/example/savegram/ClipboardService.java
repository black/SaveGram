package com.example.savegram;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ClipboardService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
      }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("service","started");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("service","stopped");
        super.onDestroy();
    }
}
