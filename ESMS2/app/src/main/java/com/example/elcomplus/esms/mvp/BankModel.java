package com.example.elcomplus.esms.mvp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.elcomplus.esms.databases.BankDataHelper;
import com.example.elcomplus.esms.databases.SmsDataHelper;
import com.example.elcomplus.esms.models.Bank;
import com.example.elcomplus.esms.models.Sms;

import org.json.JSONException;
import org.json.JSONObject;

public class BankModel {
    private static final String TAG = "BankModel";
    private BankCallBack bankCallBack;
    private BankPresenter bankPresenter;
    BankDataHelper bankDataHelper;
    SmsDataHelper smsDataHelper;
    Context context;
    Bank bank;
    Sms sms;
    public BankModel(Context context, BankCallBack bankCallBack){
        this.bankCallBack=bankCallBack;
        this.context=context;
        bankDataHelper=new BankDataHelper(context);
        smsDataHelper=new SmsDataHelper(context);

    }
    public void insertBank(Bank bank){
        long id = bankDataHelper.addBank(bank);
        if(id<0){
            bankCallBack.onAddFailure("Add Failure");
        }
        else {
            bank.setId(((int) id));
            bankCallBack.onAddBankSuccess(bank);
        }
    }


    public void deleteBank(int id){
        bankDataHelper.deletebank(id);
        bankDataHelper.getAll();
        bankCallBack.onDeleteSuccess("Notify","Success delete");
    }

    public void mGetList(){
        bankCallBack.showAllBank(bankDataHelper.getAll());
    }





}
