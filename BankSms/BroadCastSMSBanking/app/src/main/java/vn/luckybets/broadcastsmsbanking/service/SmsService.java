package vn.luckybets.broadcastsmsbanking.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import vn.luckybets.broadcastsmsbanking.reciver.SmsReciver;

public class SmsService extends Service {

    private SmsReciver smsReciver;
    public SmsService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        smsReciver=new SmsReciver();
        registerReceiver(smsReciver,new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Intent broadcastIntent = new Intent("ac.in.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent intent = new Intent("com.android.ServiceStopped");
        sendBroadcast(intent);
    }
}
