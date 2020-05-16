package com.example.savegram.ClipBoardManager;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.example.savegram.MainActivity;
import com.example.savegram.R;

import static com.example.savegram.ClipBoardManager.ClipNotification.CHANNEL_ID;

public class ClipboardService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("SAVEGRAM");

        Intent notficationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,notficationIntent,0);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentText("SAVE GRAM")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_photo_black_24dp)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(101,notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
