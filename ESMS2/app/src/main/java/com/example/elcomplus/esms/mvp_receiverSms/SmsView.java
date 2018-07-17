package com.example.elcomplus.esms.mvp_receiverSms;

import com.example.elcomplus.esms.models.Bank;
import com.example.elcomplus.esms.models.Sms;

import java.util.List;

public interface SmsView {
    void displayBank(List<Bank> list);
    void displaySms(List<Sms> list);
    void showDialog(String title, String message);
    void onSuccessRequest(List<Sms> list);
    void onFaileRequest(String title, String mesage);
}
