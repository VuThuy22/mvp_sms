package com.example.elcomplus.esms.mvp;

import android.content.Context;
import android.util.Log;

import com.example.elcomplus.esms.MainActivity;
import com.example.elcomplus.esms.models.Bank;
import com.example.elcomplus.esms.models.Sms;

import java.util.List;

public class BankPresenter implements BankCallBack {
    private BankView bankView;
    private BankModel bankModel;
    private static final String TAG = "BankPresenter";
    String URL = "http://101.99.23.175:5566";


    public BankPresenter(Context context,BankView bankView) {
        this.bankView = bankView;
        this.bankModel=new BankModel(context, this);
    }



    public void delete(int id){
        bankModel.deleteBank(id);
    }
    public void insert(Bank bank){
        bankModel.insertBank(bank);
    }
    public void getList(){
        bankModel.mGetList();
    }
    @Override
    public void onAddBankSuccess(Bank bank) {
        getList();
    }


    @Override
    public void onAddFailure(String message) {
        bankView.showDialog("Error!",message);
    }

    @Override
    public void onDeleteSuccess(String title, String message) {
        getList();
    }

    @Override
    public void onDeleteFailure(String title, String message) {

    }

    @Override
    public void showAllBank(List<Bank> list) {
        bankView.displayBank(list);
    }


}
