package com.example.elcomplus.esms.mvp;

import com.example.elcomplus.esms.models.Bank;
import com.example.elcomplus.esms.models.Sms;

import java.util.List;

public interface BankView {
    void displayBank(List<Bank> list);
    void displaySms(List<Sms> list);
    void showDialog(String title, String message);
}
