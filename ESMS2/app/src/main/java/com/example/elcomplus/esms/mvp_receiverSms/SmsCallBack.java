package com.example.elcomplus.esms.mvp_receiverSms;

import com.example.elcomplus.esms.models.Bank;
import com.example.elcomplus.esms.models.Sms;

import java.util.List;

public interface SmsCallBack {
    void onAddSmsSuccess(Sms sms, int id_request);
    void onAddFailure(String message);
    void onSuccessRequest(String message);
    void onFaileRequest(String message);
    void showAllBank(List<Bank> list);
    void showAllSms(List<Sms> list);
}
