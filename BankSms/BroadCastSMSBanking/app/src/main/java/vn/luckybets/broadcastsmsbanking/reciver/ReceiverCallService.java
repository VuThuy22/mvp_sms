package vn.luckybets.broadcastsmsbanking.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import vn.luckybets.broadcastsmsbanking.service.SmsService;

public class ReceiverCallService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
            context.startService(new Intent(context, SmsService.class));
    }
}
