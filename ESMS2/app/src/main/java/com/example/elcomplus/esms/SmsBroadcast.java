package com.example.elcomplus.esms;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.elcomplus.esms.Utils.TimeUtils;
import com.example.elcomplus.esms.databases.BankDataHelper;
import com.example.elcomplus.esms.databases.SmsDataHelper;
import com.example.elcomplus.esms.models.Bank;
import com.example.elcomplus.esms.models.Sms;
import com.example.elcomplus.esms.mvp.BankPresenter;
import com.example.elcomplus.esms.mvp.BankView;
import com.example.elcomplus.esms.mvp_receiverSms.SmsPresenter;
import com.example.elcomplus.esms.mvp_receiverSms.SmsView;

import java.util.ArrayList;
import java.util.List;

public class SmsBroadcast extends BroadcastReceiver implements SmsView {
    String bankName, phone_accout, content,time;
    BankDataHelper bankDataHelper;
    SmsDataHelper smsDataHelper;
    List<String> list;
    Sms sms;
    Context context;
    private SmsPresenter smsPresenter;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {
            Bundle bundle = intent.getExtras();
            bankDataHelper=new BankDataHelper(context);
            smsDataHelper=new SmsDataHelper(context);
            list=new ArrayList<>();
            smsPresenter=new SmsPresenter(context,this );
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    phone_accout="";
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    content=smsMessage.getDisplayMessageBody();
                    bankName=smsMessage.getOriginatingAddress();
                    long Id_request=smsMessage.getTimestampMillis();
                    time=TimeUtils.convertLongToddMMyyyy(Id_request);
                    list.addAll(bankDataHelper.getBankPhone());
                    for(int j=0;j<list.size();j++){
                        if(bankName.equals(list.get(j))){

                            sms=new Sms(bankName,phone_accout,content,0,time);
                               smsPresenter.insertSms(sms,(int)Id_request);
                        }
                    }

                }
            }
        }
    }

    @Override
    public void displayBank(List<Bank> list) {

    }

    @Override
    public void displaySms(List<Sms> list) {

    }

    @Override
    public void showDialog(String title, String message) {
        Intent intent1 = new Intent("receiverSms");
        context.sendBroadcast(intent1);
    }

    @Override
    public void onSuccessRequest(List<Sms> list) {

    }

    @Override
    public void onFaileRequest(String title, String mesage) {

    }
}
