package com.example.elcomplus.esms.mvp;

import com.example.elcomplus.esms.models.Bank;
import com.example.elcomplus.esms.models.Sms;

import java.util.List;

public interface BankCallBack {
    void onAddBankSuccess(Bank bank);

    void onAddFailure(String message);
    void onDeleteSuccess(String title, String message);
    void onDeleteFailure(String title, String message);
    void showAllBank(List<Bank> list);


}
