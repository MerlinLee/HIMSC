package cn.edu.jlu.limf.himsc.controller.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.Date;

import cn.edu.jlu.limf.himsc.controller.Receivers.AlarmReceiver;

public class AlarmService extends Service {
    private static final String TAG = "Merlin";
    private Intent i;
    private AlarmManager alarmManager;
    private AlarmReceiver alarmReceiver;

    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        alarmReceiver = new AlarmReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "executed at " + new Date());
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int alarmTime = 10 * 1000; // 定时10s
        long trigerAtTime = SystemClock.elapsedRealtime() + alarmTime;
        i = new Intent(this, alarmReceiver.getClass());
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, trigerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(alarmReceiver);
    }
}
